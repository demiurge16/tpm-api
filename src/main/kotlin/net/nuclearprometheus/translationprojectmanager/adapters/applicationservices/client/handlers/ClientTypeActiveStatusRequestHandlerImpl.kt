package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.handlers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.mappers.toActiveStatusResponse
import net.nuclearprometheus.translationprojectmanager.domain.model.client.ClientTypeId
import net.nuclearprometheus.translationprojectmanager.domain.ports.services.client.ClientTypeService
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientTypeActiveStatusRequestHandlerImpl(
    private val service: ClientTypeService
) : ClientTypeActiveStatusRequestHandler {

    override fun activate(id: UUID) = service.activate(ClientTypeId(id)).toActiveStatusResponse()

    override fun deactivate(id: UUID) = service.deactivate(ClientTypeId(id)).toActiveStatusResponse()
}