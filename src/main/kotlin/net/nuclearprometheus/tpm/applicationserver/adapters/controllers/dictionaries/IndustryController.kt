package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.dictionaries

import com.opencsv.bean.StatefulBeanToCsvBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.responses.ValidationErrorResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.IndustryApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.CreateIndustry
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.ListIndustries
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.UpdateIndustry
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.Industry as IndustryResponse
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
@RequestMapping("/api/v1/industry")
class IndustryController(private val service: IndustryApplicationService) {

    private val logger = loggerFor(IndustryController::class.java)

    @GetMapping("")
    fun getAll(query: ListIndustries) = with(logger) {
        info("GET /api/v1/industry")
        ResponseEntity.ok().body(service.getIndustries(query))
    }

    @GetMapping("/export", produces = ["text/csv"])
    fun export(query: ListIndustries) = with(logger) {
        info("GET /api/v1/industry/export")

        val industries = service.getIndustries(query)

        val output = PipedOutputStream()
        val input = PipedInputStream(output)

        thread {
            val writer = OutputStreamWriter(output)
            val beanToCsv = StatefulBeanToCsvBuilder<IndustryResponse>(writer).build()
            beanToCsv.write(industries.items)
            writer.flush()
            writer.close()
            output.close()
        }

        val name = "industries-${LocalDate.now()}.csv"
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

    @GetMapping("/{industryId}")
    fun get(@PathVariable(name = "industryId") id: UUID) = with(logger) {
        info("GET /api/v1/industry/$id")
        ResponseEntity.ok().body(service.getIndustry(id))
    }

    @PostMapping("")
    fun create(@RequestBody request: CreateIndustry) = with(logger) {
        info("POST /api/v1/industry")
        ResponseEntity.ok().body(service.createIndustry(request))
    }

    @PutMapping("/{industryId}")
    fun update(@PathVariable(name = "industryId") id: UUID, @RequestBody request: UpdateIndustry) = with(logger) {
        info("PUT /api/v1/industry/$id")
        ResponseEntity.ok().body(service.updateIndustry(id, request))
    }

    @PatchMapping("/{industryId}/activate")
    fun activate(@PathVariable(name = "industryId") id: UUID) = with(logger) {
        info("PATCH /api/v1/industry/$id/activate")
        ResponseEntity.ok().body(service.activateIndustry(id))
    }

    @PatchMapping("/{industryId}/deactivate")
    fun deactivate(@PathVariable(name = "industryId") id: UUID) = with(logger) {
        info("PATCH /api/v1/industry/$id/deactivate")
        ResponseEntity.ok().body(service.deactivateIndustry(id))
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
