package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.handlers.ClientActiveStatusRequestHandler
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.handlers.ClientCreateRequestHandler
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.handlers.ClientUpdateRequestHandler
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.handlers.ClientViewRequestHandler
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.requests.ClientCreateRequest
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.requests.ClientUpdateRequest
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.responses.ClientActiveStatusResponse
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.responses.ClientView
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientApplicationServiceImpl(
    private val clientTypeViewRequestHandler: ClientViewRequestHandler,
    private val clientTypeCreateRequestHandler: ClientCreateRequestHandler,
    private val clientTypeUpdateRequestHandler: ClientUpdateRequestHandler,
    private val clientTypeActiveStatusRequestHandler: ClientActiveStatusRequestHandler
) : ClientApplicationService {

    override fun getClientTypes(): List<ClientView>  = clientTypeViewRequestHandler.getClients()

    override fun getClientType(id: UUID): ClientView = clientTypeViewRequestHandler.getClient(id)

    override fun createClientType(request: ClientCreateRequest) = clientTypeCreateRequestHandler.create(request)

    override fun updateClientType(id: UUID, request: ClientUpdateRequest) = clientTypeUpdateRequestHandler.update(id, request)

    override fun activate(id: UUID): ClientActiveStatusResponse = clientTypeActiveStatusRequestHandler.activate(id)

    override fun deactivate(id: UUID): ClientActiveStatusResponse = clientTypeActiveStatusRequestHandler.deactivate(id)
}
