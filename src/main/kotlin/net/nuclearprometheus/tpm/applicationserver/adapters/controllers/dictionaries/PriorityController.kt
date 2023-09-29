package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.dictionaries

import com.opencsv.bean.StatefulBeanToCsvBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.responses.ValidationErrorResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.PriorityApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.CreatePriority
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.ListPriorities
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.UpdatePriority
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.Priority as PriorityResponse
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
@RequestMapping("/api/v1/priority")
class PriorityController(private val service: PriorityApplicationService) {

    private val logger = loggerFor(PriorityController::class.java)

    @GetMapping("")
    fun getAll(query: ListPriorities) = with(logger) {
        info("GET /api/v1/priority")

        ResponseEntity.ok().body(service.getPriorities(query))
    }

    @GetMapping("/export", produces = ["text/csv"])
    fun export(query: ListPriorities) = with(logger) {
        info("GET /api/v1/priority/export")

        val priorities = service.getPriorities(query)

        val output = PipedOutputStream()
        val input = PipedInputStream(output)

        thread {
            val writer = OutputStreamWriter(output)
            val beanToCsv = StatefulBeanToCsvBuilder<PriorityResponse>(writer).build()
            beanToCsv.write(priorities.items)
            writer.flush()
            writer.close()
            output.close()
        }

        val name = "priorities-${LocalDate.now()}.csv"
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

    @GetMapping("/{priorityId}")
    fun get(@PathVariable(name = "priorityId") id: UUID) = with(logger) {
        info("GET /api/v1/priority/$id")

        ResponseEntity.ok().body(service.getPriority(id))
    }

    @PostMapping("")
    fun create(@RequestBody request: CreatePriority) = with(logger) {
        info("POST /api/v1/priority")

        ResponseEntity.ok().body(service.createPriority(request))
    }

    @PutMapping("/{priorityId}")
    fun update(@PathVariable(name = "priorityId") id: UUID, @RequestBody request: UpdatePriority) = with(logger) {
        info("PUT /api/v1/priority/$id")

        ResponseEntity.ok().body(service.updatePriority(id, request))
    }

    @PatchMapping("/{priorityId}/activate")
    fun activate(@PathVariable(name = "priorityId") id: UUID) = with(logger) {
        info("PATCH /api/v1/priority/$id/activate")

        ResponseEntity.ok().body(service.activatePriority(id))
    }

    @PatchMapping("/{priorityId}/deactivate")
    fun deactivate(@PathVariable(name = "priorityId") id: UUID) = with(logger) {
        info("PATCH /api/v1/priority/$id/deactivate")

        ResponseEntity.ok().body(service.deactivatePriority(id))
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
