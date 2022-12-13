package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.handlers.*
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.requests.*
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.responses.*
import org.springframework.stereotype.Service
import java.util.*

@Service class ClientTypeApplicationServiceImpl(
    private val clientTypeViewRequestHandler: ClientTypeViewRequestHandler,
    private val clientTypeCreateRequestHandler: ClientTypeCreateRequestHandler,
    private val clientTypeUpdateRequestHandler: ClientTypeUpdateRequestHandler,
    private val clientTypeActiveStatusRequestHandler: ClientTypeActiveStatusRequestHandler
) : ClientTypeApplicationService {

    override fun getClientTypes(): List<ClientTypeView>  = clientTypeViewRequestHandler.getClientTypes()

    override fun getClientType(id: UUID): ClientTypeView = clientTypeViewRequestHandler.getClientType(id)

    override fun createClientType(request: ClientTypeCreateRequest) = clientTypeCreateRequestHandler.createClientType(request)

    override fun updateClientType(id: UUID, request: ClientTypeUpdateRequest) = clientTypeUpdateRequestHandler.updateClientType(id, request)

    override fun activate(id: UUID): ClientTypeActiveStatusResponse = clientTypeActiveStatusRequestHandler.activate(id)

    override fun deactivate(id: UUID): ClientTypeActiveStatusResponse = clientTypeActiveStatusRequestHandler.deactivate(id)
}
