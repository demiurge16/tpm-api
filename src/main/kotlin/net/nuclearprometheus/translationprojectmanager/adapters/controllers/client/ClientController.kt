package net.nuclearprometheus.translationprojectmanager.adapters.controllers.client

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.ClientApplicationService
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.requests.ClientCreateRequest
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.requests.ClientUpdateRequest
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/client")
class ClientController(private val service: ClientApplicationService) {

    @GetMapping("")
    fun getClientTypes() = service.getClientTypes()

    @GetMapping("/{id}")
    fun getClientTypeById(@PathVariable(name = "id") id: UUID) = service.getClientType(id)

    @PostMapping("")
    fun createClientType(@RequestBody request: ClientCreateRequest) = service.createClientType(request)

    @PutMapping("/{id}")
    fun updateClientType(@PathVariable(name = "id") id: UUID, @RequestBody request: ClientUpdateRequest) =
        service.updateClientType(id, request)

    @PatchMapping("/{id}/activate")
    fun activate(@PathVariable(name = "id") id: UUID) = service.activate(id)

    @PatchMapping("/{id}/deactivate")
    fun deactivate(@PathVariable(name = "id") id: UUID) = service.deactivate(id)

}
