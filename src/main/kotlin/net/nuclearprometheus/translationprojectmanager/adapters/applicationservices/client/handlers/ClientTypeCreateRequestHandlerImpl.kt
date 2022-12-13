package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.handlers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.mappers.toCreateResponse
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.requests.ClientTypeCreateRequest
import net.nuclearprometheus.translationprojectmanager.domain.ports.services.client.ClientTypeService
import org.springframework.stereotype.Service

@Service
class ClientTypeCreateRequestHandlerImpl(private val service: ClientTypeService) : ClientTypeCreateRequestHandler {
    override fun createClientType(request: ClientTypeCreateRequest) =
        service.create(request.name, request.description, request.corporate).toCreateResponse()
}
