package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.IndustryApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.IndustryRequest
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/industry")
class IndustryController(private val service: IndustryApplicationService) {

    private val logger = loggerFor(IndustryController::class.java)

    @GetMapping("")
    fun getAll() = with(logger) {
        info("GET /api/v1/industry")

        service.getIndustries()
    }

    @GetMapping("/{id}")
    fun get(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("GET /api/v1/industry/$id")

        service.getIndustry(id)
    }

    @PostMapping("")
    fun create(@RequestBody request: IndustryRequest.Create) = with(logger) {
        info("POST /api/v1/industry")

        service.createIndustry(request)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable(name = "id") id: UUID, @RequestBody request: IndustryRequest.Update) = with(logger) {
        info("PUT /api/v1/industry/$id")

        service.updateIndustry(id, request)
    }

    @PatchMapping("/{id}/activate")
    fun activate(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/industry/$id/activate")

        service.activateIndustry(id)
    }

    @PatchMapping("/{id}/deactivate")
    fun deactivate(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/industry/$id/deactivate")

        service.deactivateIndustry(id)
    }
}
