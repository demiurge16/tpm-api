package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.handlers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.mappers.toActiveStatusResponse
import net.nuclearprometheus.translationprojectmanager.domain.model.client.ClientId
import net.nuclearprometheus.translationprojectmanager.domain.ports.services.client.ClientService
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientActiveStatusRequestHandlerImpl(private val service: ClientService) : ClientActiveStatusRequestHandler {

    override fun activate(id: UUID) = service.activate(ClientId(id)).toActiveStatusResponse()
    override fun deactivate(id: UUID) = service.deactivate(ClientId(id)).toActiveStatusResponse()
}