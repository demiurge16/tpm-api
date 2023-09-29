package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.dictionaries

import com.opencsv.bean.StatefulBeanToCsvBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.responses.ValidationErrorResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.ServiceTypeApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.CreateServiceTypes
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.ListServiceTypes
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.UpdateServiceTypes
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.ServiceType as ServiceTypeResponse
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
import java.util.*
import kotlin.concurrent.thread

@RestController
@RequestMapping("/api/v1/service-type")
class ServiceTypeController(private val service: ServiceTypeApplicationService) {

    private val logger = loggerFor(ServiceTypeController::class.java)

    @GetMapping("")
    fun getAll(query: ListServiceTypes) = with(logger) {
        info("GET /api/v1/service-type")
        ResponseEntity.ok().body(service.getServiceTypes(query))
    }

    @GetMapping("/export", produces = ["text/csv"])
    fun export(query: ListServiceTypes) = with(logger) {
        info("GET /api/v1/service-type/export")

        val serviceTypes = service.getServiceTypes(query)

        val output = PipedOutputStream()
        val input = PipedInputStream(output)

        thread {
            val writer = OutputStreamWriter(output)
            val beanToCsv = StatefulBeanToCsvBuilder<ServiceTypeResponse>(writer).build()
            beanToCsv.write(serviceTypes.items)
            writer.flush()
            writer.close()
            output.close()
        }

        val name = "service-types-${LocalDate.now()}.csv"
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
            .contentType(MediaType.parseMediaType("text/csv"))
            .body(resource)
    }

    @GetMapping("/{serviceTypeId}")
    fun get(@PathVariable(name = "serviceTypeId") id: UUID) = with(logger) {
        info("GET /api/v1/service-type/$id")
        ResponseEntity.ok().body(service.getServiceType(id))
    }

    @PostMapping("")
    fun create(@RequestBody request: CreateServiceTypes) = with(logger) {
        info("POST /api/v1/service-type")
        ResponseEntity.ok().body(service.createServiceType(request))
    }

    @PutMapping("/{serviceTypeId}")
    fun update(@PathVariable(name = "serviceTypeId") id: UUID, @RequestBody request: UpdateServiceTypes) = with(logger) {
        info("PUT /api/v1/service-type/$id")
        ResponseEntity.ok().body(service.updateServiceType(id, request))
    }

    @PatchMapping("/{serviceTypeId}/activate")
    fun activate(@PathVariable(name = "serviceTypeId") id: UUID) = with(logger) {
        info("PATCH /api/v1/service-type/$id/activate")
        ResponseEntity.ok().body(service.activateServiceType(id))
    }

    @PatchMapping("/{serviceTypeId}/deactivate")
    fun deactivate(@PathVariable(name = "serviceTypeId") id: UUID) = with(logger) {
        info("PATCH /api/v1/service-type/$id/deactivate")
        ResponseEntity.ok().body(service.deactivateServiceType(id))
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
    fun handleIllegalStateException(e: IllegalStateException): ResponseEntity<Unit> {
        logger.warn("IllegalStateException: ${e.message}")
        return ResponseEntity.internalServerError().build()
    }
}