package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers.toUpdateResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.ClientTypeUpdateRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientTypeId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client.ClientTypeService
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientTypeUpdateRequestHandlerImpl(private val service: ClientTypeService) :
    net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers.ClientTypeUpdateRequestHandler {

    override fun updateClientType(id: UUID, request: ClientTypeUpdateRequest) =
        service.update(ClientTypeId(id), request.name, request.description, request.corporate).toUpdateResponse()
}
