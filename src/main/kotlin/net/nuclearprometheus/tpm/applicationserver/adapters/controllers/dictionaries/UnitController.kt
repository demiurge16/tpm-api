package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.dictionaries

import com.opencsv.bean.StatefulBeanToCsvBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.responses.ValidationErrorResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.UnitApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.CreateUnit
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.ListUnits
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.UpdateUnit
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.Unit as UnitResponse
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
@RequestMapping("/api/v1/unit")
class UnitController(private val service: UnitApplicationService) {

    private val logger = loggerFor(UnitController::class.java)

    @GetMapping("")
    fun getAll(query: ListUnits) = with(logger) {
        info("GET /api/v1/unit")
        ResponseEntity.ok().body(service.getUnits(query))
    }

    @GetMapping("/export", produces = ["text/csv"])
    fun export(query: ListUnits) = with(logger) {
        info("GET /api/v1/unit/export")

        val units = service.getUnits(query)

        val output = PipedOutputStream()
        val input = PipedInputStream(output)

        thread {
            val writer = OutputStreamWriter(output)
            val beanToCsv = StatefulBeanToCsvBuilder<UnitResponse>(writer).build()
            beanToCsv.write(units.items)
            writer.flush()
            writer.close()
            output.close()
        }

        val name = "units-${LocalDate.now()}.csv"
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

    @GetMapping("/{unitId}")
    fun get(@PathVariable(name = "unitId") id: UUID) = with(logger) {
        info("GET /api/v1/unit/$id")
        ResponseEntity.ok().body(service.getUnit(id))
    }

    @PostMapping("")
    fun create(@RequestBody request: CreateUnit) = with(logger) {
        info("POST /api/v1/unit")
        ResponseEntity.ok().body(service.createUnit(request))
    }

    @PutMapping("/{unitId}")
    fun update(@PathVariable(name = "unitId") id: UUID, @RequestBody request: UpdateUnit) = with(logger) {
        info("PUT /api/v1/unit/$id")
        ResponseEntity.ok().body(service.updateUnit(id, request))
    }

    @PatchMapping("/{unitId}/activate")
    fun activate(@PathVariable(name = "unitId") id: UUID) = with(logger) {
        info("PATCH /api/v1/unit/$id/activate")
        ResponseEntity.ok().body(service.activateUnit(id))
    }

    @PatchMapping("/{unitId}/deactivate")
    fun deactivate(@PathVariable(name = "unitId") id: UUID) = with(logger) {
        info("PATCH /api/v1/unit/$id/deactivate")
        ResponseEntity.ok().body(service.deactivateUnit(id))
    }

    @GetMapping("/refdata/measurement")
    fun getMeasurementRefData() = with(logger) {
        info("GET /api/v1/unit/refdata/measurement")
        ResponseEntity.ok().body(service.getMeasurements())
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
