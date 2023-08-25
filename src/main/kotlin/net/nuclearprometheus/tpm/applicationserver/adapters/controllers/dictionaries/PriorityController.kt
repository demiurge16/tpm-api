package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.PriorityApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.AccuracyRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.PriorityRequest
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/priority")
class PriorityController(private val service: PriorityApplicationService) {

    private val logger = loggerFor(PriorityController::class.java)

    @GetMapping("")
    fun getAll(query: PriorityRequest.List) = with(logger) {
        info("GET /api/v1/priority")

        ResponseEntity.ok().body(service.getPriorities(query))
    }

    @GetMapping("/{id}")
    fun get(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("GET /api/v1/priority/$id")

        ResponseEntity.ok().body(service.getPriority(id))
    }

    @PostMapping("")
    fun create(@RequestBody request: PriorityRequest.Create) = with(logger) {
        info("POST /api/v1/priority")

        ResponseEntity.ok().body(service.createPriority(request))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable(name = "id") id: UUID, @RequestBody request: PriorityRequest.Update) = with(logger) {
        info("PUT /api/v1/priority/$id")

        ResponseEntity.ok().body(service.updatePriority(id, request))
    }

    @PatchMapping("/{id}/activate")
    fun activate(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/priority/$id/activate")

        ResponseEntity.ok().body(service.activatePriority(id))
    }

    @PatchMapping("/{id}/deactivate")
    fun deactivate(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/priority/$id/deactivate")

        ResponseEntity.ok().body(service.deactivatePriority(id))
    }
}
