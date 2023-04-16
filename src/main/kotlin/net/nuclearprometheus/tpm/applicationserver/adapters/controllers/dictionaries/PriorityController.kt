package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.PriorityApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.PriorityRequest
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/priority")
class PriorityController(private val service: PriorityApplicationService) {

    private val logger = loggerFor(PriorityController::class.java)

    @GetMapping("")
    fun getAll() = with(logger) {
        info("GET /api/v1/priority")

        service.getPriorities()
    }

    @GetMapping("/{id}")
    fun get(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("GET /api/v1/priority/$id")

        service.getPriority(id)
    }

    @PostMapping("")
    fun create(@RequestBody request: PriorityRequest.Create) = with(logger) {
        info("POST /api/v1/priority")

        service.createPriority(request)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable(name = "id") id: UUID, @RequestBody request: PriorityRequest.Update) = with(logger) {
        info("PUT /api/v1/priority/$id")

        service.updatePriority(id, request)
    }

    @PatchMapping("/{id}/activate")
    fun activate(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/priority/$id/activate")

        service.activatePriority(id)
    }

    @PatchMapping("/{id}/deactivate")
    fun deactivate(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/priority/$id/deactivate")

        service.deactivatePriority(id)
    }
}
