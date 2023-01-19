package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers.ClientActiveStatusRequestHandler
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers.ClientCreateRequestHandler
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers.ClientUpdateRequestHandler
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers.ClientViewRequestHandler
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.ClientCreateRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.ClientUpdateRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientActiveStatusResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientView
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientApplicationServiceImpl(
    private val clientTypeViewRequestHandler: ClientViewRequestHandler,
    private val clientTypeCreateRequestHandler: net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers.ClientCreateRequestHandler,
    private val clientTypeUpdateRequestHandler: ClientUpdateRequestHandler,
    private val clientTypeActiveStatusRequestHandler: net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers.ClientActiveStatusRequestHandler
) : net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.ClientApplicationService {

    override fun getClientTypes(): List<ClientView>  = clientTypeViewRequestHandler.getClients()

    override fun getClientType(id: UUID): ClientView = clientTypeViewRequestHandler.getClient(id)

    override fun createClientType(request: ClientCreateRequest) = clientTypeCreateRequestHandler.create(request)

    override fun updateClientType(id: UUID, request: ClientUpdateRequest) = clientTypeUpdateRequestHandler.update(id, request)

    override fun activate(id: UUID): ClientActiveStatusResponse = clientTypeActiveStatusRequestHandler.activate(id)

    override fun deactivate(id: UUID): ClientActiveStatusResponse = clientTypeActiveStatusRequestHandler.deactivate(id)
}
