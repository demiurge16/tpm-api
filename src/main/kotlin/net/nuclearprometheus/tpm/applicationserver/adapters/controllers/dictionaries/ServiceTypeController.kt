package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.ServiceTypeApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.AccuracyRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.ServiceTypeRequest
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/service-type")
class ServiceTypeController(private val service: ServiceTypeApplicationService) {

    private val logger = loggerFor(ServiceTypeController::class.java)

    @GetMapping("")
    fun getAll(query: ServiceTypeRequest.List) = with(logger) {
        info("GET /api/v1/service-type")
        ResponseEntity.ok().body(service.getServiceTypes(query))
    }

    @GetMapping("/{id}")
    fun get(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("GET /api/v1/service-type/$id")
        ResponseEntity.ok().body(service.getServiceType(id))
    }

    @PostMapping("")
    fun create(@RequestBody request: ServiceTypeRequest.Create) = with(logger) {
        info("POST /api/v1/service-type")
        ResponseEntity.ok().body(service.createServiceType(request))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable(name = "id") id: UUID, @RequestBody request: ServiceTypeRequest.Update) = with(logger) {
        info("PUT /api/v1/service-type/$id")
        ResponseEntity.ok().body(service.updateServiceType(id, request))
    }

    @PatchMapping("/{id}/activate")
    fun activate(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/service-type/$id/activate")
        ResponseEntity.ok().body(service.activateServiceType(id))
    }

    @PatchMapping("/{id}/deactivate")
    fun deactivate(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/service-type/$id/deactivate")
        ResponseEntity.ok().body(service.deactivateServiceType(id))
    }
}