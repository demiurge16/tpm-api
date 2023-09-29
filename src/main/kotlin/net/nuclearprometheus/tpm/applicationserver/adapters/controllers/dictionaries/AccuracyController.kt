package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.dictionaries

import com.opencsv.bean.StatefulBeanToCsvBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.responses.ValidationErrorResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.AccuracyApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.CreateAccuracy
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.ListAccuracies
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.UpdateAccuracy
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.Accuracy as AccuracyResponse
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
@RequestMapping("/api/v1/accuracy")
class AccuracyController(private val service: AccuracyApplicationService) {

    private val logger = loggerFor(AccuracyController::class.java)

    @GetMapping("")
    fun getAll(query: ListAccuracies) = with(logger) {
        info("GET /api/v1/accuracy")

        ResponseEntity.ok().body(service.getAccuracies(query))
    }

    @GetMapping("/export", produces = ["text/csv"])
    fun export(query: ListAccuracies) = with(logger) {
        info("GET /api/v1/accuracy/export")

        val accuracies = service.getAccuracies(query)

        val output = PipedOutputStream()
        val input = PipedInputStream(output)

        thread {
            val writer = OutputStreamWriter(output)
            val beanToCsv = StatefulBeanToCsvBuilder<AccuracyResponse>(writer).build()
            beanToCsv.write(accuracies.items)
            writer.flush()
            writer.close()
            output.close()
        }

        val name = "accuracies-${LocalDate.now()}.csv"
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

    @GetMapping("/{accuracyId}")
    fun get(@PathVariable(name = "accuracyId") id: UUID) = with(logger) {
        info("GET /api/v1/accuracy/$id")

        ResponseEntity.ok().body(service.getAccuracy(id))
    }

    @PostMapping("")
    fun create(@RequestBody request: CreateAccuracy) = with(logger) {
        info("POST /api/v1/accuracy")

        ResponseEntity.ok().body(service.createAccuracy(request))
    }

    @PutMapping("/{accuracyId}")
    fun update(@PathVariable(name = "accuracyId") id: UUID, @RequestBody request: UpdateAccuracy) = with(logger) {
        info("PUT /api/v1/accuracy/$id")

        ResponseEntity.ok().body(service.updateAccuracy(id, request))
    }

    @PatchMapping("/{accuracyId}/activate")
    fun activate(@PathVariable(name = "accuracyId") id: UUID) = with(logger) {
        info("PATCH /api/v1/accuracy/$id/activate")

        ResponseEntity.ok().body(service.activateAccuracy(id))
    }

    @PatchMapping("/{accuracyId}/deactivate")
    fun deactivate(@PathVariable(name = "accuracyId") id: UUID) = with(logger) {
        info("PATCH /api/v1/accuracy/$id/deactivate")

        ResponseEntity.ok().body(service.deactivateAccuracy(id))
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

