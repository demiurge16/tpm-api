package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.UnitApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.UnitRequest
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/unit")
class UnitController(private val service: UnitApplicationService) {

    private val logger = loggerFor(UnitController::class.java)

    @GetMapping("")
    fun getAll() = with(logger) {
        info("GET /api/v1/unit")

        service.getUnits()
    }

    @GetMapping("/{id}")
    fun get(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("GET /api/v1/unit/$id")

        service.getUnit(id)
    }

    @PostMapping("")
    fun create(@RequestBody request: UnitRequest.Create) = with(logger) {
        info("POST /api/v1/unit")

        service.createUnit(request)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable(name = "id") id: UUID, @RequestBody request: UnitRequest.Update) = with(logger) {
        info("PUT /api/v1/unit/$id")

        service.updateUnit(id, request)
    }

    @PatchMapping("/{id}/activate")
    fun activate(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/unit/$id/activate")

        service.activateUnit(id)
    }

    @PatchMapping("/{id}/deactivate")
    fun deactivate(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/unit/$id/deactivate")

        service.deactivateUnit(id)
    }
}

