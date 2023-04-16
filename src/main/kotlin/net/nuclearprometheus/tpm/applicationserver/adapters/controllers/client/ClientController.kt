package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.client

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.ClientApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.ClientRequest
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/client")
class ClientController(private val service: ClientApplicationService) {

    private val logger = loggerFor(this::class.java)

    @GetMapping("")
    fun getClient(query: ClientRequest.List) =
        with(logger) {
            info("GET /api/v1/client")

            ResponseEntity.ok().body(service.getClients(query))
        }

    @GetMapping("/{id}")
    fun getClientById(@PathVariable(name = "id") id: UUID) =
        with(logger) {
            info("GET /api/v1/client/$id")

            ResponseEntity.ok().body(service.getClient(id))
        }

    @PostMapping("")
    fun createClient(@RequestBody request: ClientRequest.Create) =
        with(logger) {
            info("POST /api/v1/client")

            ResponseEntity.ok().body(service.createClient(request))
        }

    @PutMapping("/{id}")
    fun updateClient(@PathVariable(name = "id") id: UUID, @RequestBody request: ClientRequest.Update) =
        with(logger) {
            info("PUT /api/v1/client/$id")

            ResponseEntity.ok().body(service.updateClient(id, request))
        }

    @PatchMapping("/{id}/activate")
    fun activate(@PathVariable(name = "id") id: UUID) =
        with(logger) {
            info("PATCH /api/v1/client/$id/activate")

            ResponseEntity.ok().body(service.activateClient(id))
        }

    @PatchMapping("/{id}/deactivate")
    fun deactivate(@PathVariable(name = "id") id: UUID) =
        with(logger) {
            info("PATCH /api/v1/client/$id/deactivate")

            ResponseEntity.ok().body(service.deactivateClient(id))
        }
}
