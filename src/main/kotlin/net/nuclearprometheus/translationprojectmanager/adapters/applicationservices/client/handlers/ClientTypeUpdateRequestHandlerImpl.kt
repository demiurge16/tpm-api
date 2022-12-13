package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.handlers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.mappers.toUpdateResponse
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.requests.ClientTypeUpdateRequest
import net.nuclearprometheus.translationprojectmanager.domain.model.client.ClientTypeId
import net.nuclearprometheus.translationprojectmanager.domain.ports.services.client.ClientTypeService
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientTypeUpdateRequestHandlerImpl(private val service: ClientTypeService) : ClientTypeUpdateRequestHandler {

    override fun updateClientType(id: UUID, request: ClientTypeUpdateRequest) =
        service.update(ClientTypeId(id), request.name, request.description, request.corporate).toUpdateResponse()
}
