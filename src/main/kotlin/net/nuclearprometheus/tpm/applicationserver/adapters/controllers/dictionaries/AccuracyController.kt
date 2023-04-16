package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.AccuracyApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.AccuracyRequest
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/accuracy")
class AccuracyController(private val service: AccuracyApplicationService) {

    private val logger = loggerFor(AccuracyController::class.java)

    @GetMapping("")
    fun getAll() = with(logger) {
        info("GET /api/v1/accuracy")

        service.getAccuracies()
    }

    @GetMapping("/{id}")
    fun get(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("GET /api/v1/accuracy/$id")

        service.getAccuracy(id)
    }

    @PostMapping("")
    fun create(@RequestBody request: AccuracyRequest.Create) = with(logger) {
        info("POST /api/v1/accuracy")

        service.createAccuracy(request)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable(name = "id") id: UUID, @RequestBody request: AccuracyRequest.Update) = with(logger) {
        info("PUT /api/v1/accuracy/$id")

        service.updateAccuracy(id, request)
    }

    @PatchMapping("/{id}/activate")
    fun activate(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/accuracy/$id/activate")

        service.activateAccuracy(id)
    }

    @PatchMapping("/{id}/deactivate")
    fun deactivate(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/accuracy/$id/deactivate")

        service.deactivateAccuracy(id)
    }
}

