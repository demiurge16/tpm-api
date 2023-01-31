package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.client

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.ClientTypeApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.*
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/client-type")
class ClientTypeController(private val service: ClientTypeApplicationService) {

    @GetMapping("")
    fun getClientTypes() = service.getClientTypes()

    @GetMapping("/{id}")
    fun getClientTypeById(@PathVariable(name = "id") id: UUID) = service.getClientType(id)

    @PostMapping("")
    fun createClientType(@RequestBody request: ClientTypeCreateRequest) = service.createClientType(request)

    @PutMapping("/{id}")
    fun updateClientType(@PathVariable(name = "id") id: UUID, @RequestBody request: ClientTypeUpdateRequest) =
        service.updateClientType(id, request)

    @PatchMapping("/{id}/activate")
    fun activate(@PathVariable(name = "id") id: UUID) = service.activate(id)

    @PatchMapping("/{id}/deactivate")
    fun deactivate(@PathVariable(name = "id") id: UUID) = service.deactivate(id)

}
