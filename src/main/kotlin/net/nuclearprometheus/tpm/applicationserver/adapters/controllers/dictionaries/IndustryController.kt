package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.dictionaries

import com.opencsv.bean.StatefulBeanToCsvBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.IndustryApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.IndustryRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.IndustryResponse
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
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
    fun getAll(query: IndustryRequest.List) = with(logger) {
        info("GET /api/v1/industry")
        ResponseEntity.ok().body(service.getIndustries(query))
    }

    @GetMapping("/export", produces = ["text/csv"])
    fun export(query: IndustryRequest.List) = with(logger) {
        info("GET /api/v1/industry/export")

        val industries = service.getIndustries(query)

        val output = PipedOutputStream()
        val input = PipedInputStream(output)

        thread {
            val writer = OutputStreamWriter(output)
            val beanToCsv = StatefulBeanToCsvBuilder<IndustryResponse.Industry>(writer).build()
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
    fun create(@RequestBody request: IndustryRequest.Create) = with(logger) {
        info("POST /api/v1/industry")
        ResponseEntity.ok().body(service.createIndustry(request))
    }

    @PutMapping("/{industryId}")
    fun update(@PathVariable(name = "industryId") id: UUID, @RequestBody request: IndustryRequest.Update) = with(logger) {
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
}
