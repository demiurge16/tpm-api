package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.client

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.ClientTypeApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.*
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/client-type")
class ClientTypeController(private val service: ClientTypeApplicationService) {

    private val logger = loggerFor(this::class.java)

    @GetMapping("")
    fun getClientTypes(query: ClientTypeRequest.List) =
        with(logger) {
            info("GET /api/v1/client-type")

            ResponseEntity.ok().body(service.getClientTypes(query))
        }

    @GetMapping("/{id}")
    fun getClientTypeById(@PathVariable(name = "id") id: UUID) =
        with (logger) {
            info("GET /api/v1/client-type/$id")

            ResponseEntity.ok().body(service.getClientType(id))
        }

    @PostMapping("")
    fun createClientType(@RequestBody request: ClientTypeRequest.Create) =
        with(logger) {
            info("POST /api/v1/client-type")

            ResponseEntity.ok().body(service.createClientType(request))
        }

    @PutMapping("/{id}")
    fun updateClientType(@PathVariable(name = "id") id: UUID, @RequestBody request: ClientTypeRequest.Update) =
        with(logger) {
            info("PUT /api/v1/client-type/$id")

            ResponseEntity.ok().body(service.updateClientType(id, request))
        }

    @PatchMapping("/{id}/activate")
    fun activate(@PathVariable(name = "id") id: UUID) =
        with(logger) {
            info("PATCH /api/v1/client-type/$id/activate")

            ResponseEntity.ok().body(service.activateClientType(id))
        }

    @PatchMapping("/{id}/deactivate")
    fun deactivate(@PathVariable(name = "id") id: UUID) =
        with(logger) {
            info("PATCH /api/v1/client-type/$id/deactivate")

            ResponseEntity.ok().body(service.deactivateClientType(id))
        }

}
