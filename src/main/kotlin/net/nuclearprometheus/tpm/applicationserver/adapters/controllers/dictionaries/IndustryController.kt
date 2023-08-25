package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.IndustryApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.IndustryRequest
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/industry")
class IndustryController(private val service: IndustryApplicationService) {

    private val logger = loggerFor(IndustryController::class.java)

    @GetMapping("")
    fun getAll(query: IndustryRequest.List) = with(logger) {
        info("GET /api/v1/industry")
        ResponseEntity.ok().body(service.getIndustries(query))
    }

    @GetMapping("/{id}")
    fun get(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("GET /api/v1/industry/$id")
        ResponseEntity.ok().body(service.getIndustry(id))
    }

    @PostMapping("")
    fun create(@RequestBody request: IndustryRequest.Create) = with(logger) {
        info("POST /api/v1/industry")
        ResponseEntity.ok().body(service.createIndustry(request))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable(name = "id") id: UUID, @RequestBody request: IndustryRequest.Update) = with(logger) {
        info("PUT /api/v1/industry/$id")
        ResponseEntity.ok().body(service.updateIndustry(id, request))
    }

    @PatchMapping("/{id}/activate")
    fun activate(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/industry/$id/activate")
        ResponseEntity.ok().body(service.activateIndustry(id))
    }

    @PatchMapping("/{id}/deactivate")
    fun deactivate(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/industry/$id/deactivate")
        ResponseEntity.ok().body(service.deactivateIndustry(id))
    }
}
