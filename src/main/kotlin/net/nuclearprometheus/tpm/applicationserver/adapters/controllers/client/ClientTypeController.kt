package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.client

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.ClientTypeApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.*
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/client-type")
class ClientTypeController(private val service: ClientTypeApplicationService) {

    private val logger = loggerFor(this::class.java)

    @GetMapping("")
    fun getClientTypes(query: ClientTypeListRequest) =
        with(logger) {
            info("GET /api/v1/client-type")
            service.getClientTypes(query)
        }

    @GetMapping("/{id}")
    fun getClientTypeById(@PathVariable(name = "id") id: UUID) =
        with (logger) {
            info("GET /api/v1/client-type/$id")
            service.getClientType(id)
        }

    @PostMapping("")
    fun createClientType(@RequestBody request: ClientTypeCreateRequest) =
        with(logger) {
            info("POST /api/v1/client-type")
            service.createClientType(request)
        }

    @PutMapping("/{id}")
    fun updateClientType(@PathVariable(name = "id") id: UUID, @RequestBody request: ClientTypeUpdateRequest) =
        with(logger) {
            info("PUT /api/v1/client-type/$id")
            service.updateClientType(id, request)
        }

    @PatchMapping("/{id}/activate")
    fun activate(@PathVariable(name = "id") id: UUID) =
        with(logger) {
            info("PATCH /api/v1/client-type/$id/activate")
            service.activate(id)
        }

    @PatchMapping("/{id}/deactivate")
    fun deactivate(@PathVariable(name = "id") id: UUID) =
        with(logger) {
            info("PATCH /api/v1/client-type/$id/deactivate")
            service.deactivate(id)
        }

}
