package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.client

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.ClientApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.ClientCreateRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.ClientListRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.ClientUpdateRequest
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/client")
class ClientController(private val service: ClientApplicationService) {

    private val logger = loggerFor(this::class.java)

    @GetMapping("")
    fun getClient(query: ClientListRequest) =
        with(logger) {
            info("GET /api/v1/client")
            service.getClients(query)
        }

    @GetMapping("/{id}")
    fun getClientById(@PathVariable(name = "id") id: UUID) =
        with(logger) {
            info("GET /api/v1/client/$id")
            service.getClient(id)
        }

    @PostMapping("")
    fun createClient(@RequestBody request: ClientCreateRequest) =
        with(logger) {
            info("POST /api/v1/client")
            service.createClient(request)
        }

    @PutMapping("/{id}")
    fun updateClient(@PathVariable(name = "id") id: UUID, @RequestBody request: ClientUpdateRequest) =
        with(logger) {
            info("PUT /api/v1/client/$id")
            service.updateClient(id, request)
        }

    @PatchMapping("/{id}/activate")
    fun activate(@PathVariable(name = "id") id: UUID) =
        with(logger) {
            info("PATCH /api/v1/client/$id/activate")
            service.activate(id)
        }

    @PatchMapping("/{id}/deactivate")
    fun deactivate(@PathVariable(name = "id") id: UUID) =
        with(logger) {
            info("PATCH /api/v1/client/$id/deactivate")
            service.deactivate(id)
        }

}
