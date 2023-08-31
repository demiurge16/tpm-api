package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.dictionaries

import com.opencsv.bean.StatefulBeanToCsvBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.AccuracyApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.AccuracyRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.AccuracyResponse
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
@RequestMapping("/api/v1/accuracy")
class AccuracyController(private val service: AccuracyApplicationService) {

    private val logger = loggerFor(AccuracyController::class.java)

    @GetMapping("")
    fun getAll(query: AccuracyRequest.List) = with(logger) {
        info("GET /api/v1/accuracy")

        ResponseEntity.ok().body(service.getAccuracies(query))
    }

    @GetMapping("/export", produces = ["text/csv"])
    fun export(query: AccuracyRequest.List) = with(logger) {
        info("GET /api/v1/accuracy/export")

        val accuracies = service.getAccuracies(query)

        val output = PipedOutputStream()
        val input = PipedInputStream(output)

        thread {
            val writer = OutputStreamWriter(output)
            val beanToCsv = StatefulBeanToCsvBuilder<AccuracyResponse.Accuracy>(writer).build()
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

    @GetMapping("/{id}")
    fun get(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("GET /api/v1/accuracy/$id")

        ResponseEntity.ok().body(service.getAccuracy(id))
    }

    @PostMapping("")
    fun create(@RequestBody request: AccuracyRequest.Create) = with(logger) {
        info("POST /api/v1/accuracy")

        ResponseEntity.ok().body(service.createAccuracy(request))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable(name = "id") id: UUID, @RequestBody request: AccuracyRequest.Update) = with(logger) {
        info("PUT /api/v1/accuracy/$id")

        ResponseEntity.ok().body(service.updateAccuracy(id, request))
    }

    @PatchMapping("/{id}/activate")
    fun activate(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/accuracy/$id/activate")

        ResponseEntity.ok().body(service.activateAccuracy(id))
    }

    @PatchMapping("/{id}/deactivate")
    fun deactivate(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/accuracy/$id/deactivate")

        ResponseEntity.ok().body(service.deactivateAccuracy(id))
    }
}

