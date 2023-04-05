package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers.*
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.*
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.*
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientType
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientTypeApplicationService(
    private val clientTypeViewRequestHandler: ClientTypeViewRequestHandler,
    private val clientTypeCreateRequestHandler: ClientTypeCreateRequestHandler,
    private val clientTypeUpdateRequestHandler: ClientTypeUpdateRequestHandler,
    private val clientTypeActiveStatusRequestHandler: ClientTypeActiveStatusRequestHandler
) {

    private val logger = loggerFor(this::class.java)

    fun getClientTypes(query: FilteredRequest<ClientType>) =
        with(logger) {
            info("ClientType application service, method getClientTypes")
            clientTypeViewRequestHandler.getClientTypes(query)
        }

    fun getClientType(id: UUID) =
        with(logger) {
            info("ClientType application service, method getClientType")
            clientTypeViewRequestHandler.getClientType(id)
        }

    fun createClientType(request: ClientTypeCreateRequest) =
        with(logger) {
            info("ClientType application service, method createClientType")
            clientTypeCreateRequestHandler.createClientType(request)
        }

    fun updateClientType(id: UUID, request: ClientTypeUpdateRequest) =
        with(logger) {
            info("ClientType application service, method updateClientType")
            clientTypeUpdateRequestHandler.updateClientType(id, request)
        }

    fun activate(id: UUID): ClientTypeActiveStatusResponse =
        with(logger) {
            info("ClientType application service, method activate")
            clientTypeActiveStatusRequestHandler.activate(id)
        }

    fun deactivate(id: UUID): ClientTypeActiveStatusResponse =
        with(logger) {
            info("ClientType application service, method deactivate")
            clientTypeActiveStatusRequestHandler.deactivate(id)
        }
}
