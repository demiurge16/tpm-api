package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers.toCreateResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.ClientTypeCreateRequest
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client.ClientTypeService
import org.springframework.stereotype.Service

@Service
class ClientTypeCreateRequestHandlerImpl(private val service: ClientTypeService) :
    net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers.ClientTypeCreateRequestHandler {
    override fun createClientType(request: ClientTypeCreateRequest) =
        service.create(request.name, request.description, request.corporate).toCreateResponse()
}
