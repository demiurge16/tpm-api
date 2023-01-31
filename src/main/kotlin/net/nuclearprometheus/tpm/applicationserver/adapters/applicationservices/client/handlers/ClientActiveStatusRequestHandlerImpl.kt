package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers.toActiveStatusResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client.ClientService
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientActiveStatusRequestHandlerImpl(private val service: ClientService) :
    net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers.ClientActiveStatusRequestHandler {

    override fun activate(id: UUID) = service.activate(ClientId(id)).toActiveStatusResponse()
    override fun deactivate(id: UUID) = service.deactivate(ClientId(id)).toActiveStatusResponse()
}