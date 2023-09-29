package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.client

import com.opencsv.bean.StatefulBeanToCsvBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.ClientTypeApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.CreateClientType
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.ListClientTypes
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.UpdateClientType
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.responses.ErrorResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.responses.ValidationErrorResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientType as ClientTypeResponse
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.ValidationException
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.OutputStreamWriter
import java.io.PipedInputStream
import java.io.PipedOutputStream
import java.time.LocalDate
import java.util.UUID
import kotlin.concurrent.thread

@RestController
@RequestMapping("/api/v1/client-type")
class ClientTypeController(private val service: ClientTypeApplicationService) {

    private val logger = loggerFor(this::class.java)

    @GetMapping("")
    fun getClientTypes(query: ListClientTypes) =
        with(logger) {
            info("GET /api/v1/client-type")

            ResponseEntity.ok().body(service.getClientTypes(query))
        }

    @GetMapping("/export", produces = ["text/csv"])
    fun export(query: ListClientTypes) = with(logger) {
        info("GET /api/v1/client-type/export")

        val clientTypes = service.getClientTypes(query)

        val output = PipedOutputStream()
        val input = PipedInputStream(output)

        thread {
            val writer = OutputStreamWriter(output)
            val beanToCsv = StatefulBeanToCsvBuilder<ClientTypeResponse>(writer).build()
            beanToCsv.write(clientTypes.items)
            writer.flush()
            writer.close()
            output.close()
        }

        val name = "client-types-${LocalDate.now()}.csv"

        val resource = InputStreamResource(input)

        ResponseEntity.ok()
            .headers(
                HttpHeaders().apply {
                    add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=$name")
                    add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
                    add(HttpHeaders.PRAGMA, "no-cache")
                    add(HttpHeaders.EXPIRES, "0")
                }
            )
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(resource)
    }

    @GetMapping("/{clientTypeId}")
    fun getClientTypeById(@PathVariable(name = "clientTypeId") id: UUID) =
        with (logger) {
            info("GET /api/v1/client-type/$id")

            ResponseEntity.ok().body(service.getClientType(id))
        }

    @PostMapping("")
    fun createClientType(@RequestBody request: CreateClientType) =
        with(logger) {
            info("POST /api/v1/client-type")

            ResponseEntity.ok().body(service.createClientType(request))
        }

    @PutMapping("/{clientTypeId}")
    fun updateClientType(@PathVariable(name = "clientTypeId") id: UUID, @RequestBody request: UpdateClientType) =
        with(logger) {
            info("PUT /api/v1/client-type/$id")

            ResponseEntity.ok().body(service.updateClientType(id, request))
        }

    @PatchMapping("/{clientTypeId}/activate")
    fun activate(@PathVariable(name = "clientTypeId") id: UUID) =
        with(logger) {
            info("PATCH /api/v1/client-type/$id/activate")

            ResponseEntity.ok().body(service.activateClientType(id))
        }

    @PatchMapping("/{clientTypeId}/deactivate")
    fun deactivate(@PathVariable(name = "clientTypeId") id: UUID) =
        with(logger) {
            info("PATCH /api/v1/client-type/$id/deactivate")

            ResponseEntity.ok().body(service.deactivateClientType(id))
        }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: NotFoundException): ResponseEntity<Unit> {
        logger.warn("NotFoundException: ${e.message}")
        return ResponseEntity.notFound().build()
    }

    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(e: ValidationException): ResponseEntity<ValidationErrorResponse> {
        logger.warn("ValidationException: ${e.message}")
        return ResponseEntity.badRequest().body(ValidationErrorResponse("Validation failed", e.errors))
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(e: IllegalStateException): ResponseEntity<ErrorResponse> {
        logger.warn("IllegalStateException: ${e.message}")
        return ResponseEntity.internalServerError().body(ErrorResponse(e.message ?: "Illegal state"))
    }
}
