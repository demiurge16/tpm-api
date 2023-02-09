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
class ClientTypeApplicationServiceImpl(
    private val clientTypeViewRequestHandler: ClientTypeViewRequestHandler,
    private val clientTypeCreateRequestHandler: ClientTypeCreateRequestHandler,
    private val clientTypeUpdateRequestHandler: ClientTypeUpdateRequestHandler,
    private val clientTypeActiveStatusRequestHandler: ClientTypeActiveStatusRequestHandler
) : ClientTypeApplicationService {

    private val logger = loggerFor(this::class.java)

    override fun getClientTypes(query: FilteredRequest<ClientType>) =
        with(logger) {
            info("ClientType application service, method getClientTypes")
            clientTypeViewRequestHandler.getClientTypes(query)
        }

    override fun getClientType(id: UUID) =
        with(logger) {
            info("ClientType application service, method getClientType")
            clientTypeViewRequestHandler.getClientType(id)
        }

    override fun createClientType(request: ClientTypeCreateRequest) =
        with(logger) {
            info("ClientType application service, method createClientType")
            clientTypeCreateRequestHandler.createClientType(request)
        }

    override fun updateClientType(id: UUID, request: ClientTypeUpdateRequest) =
        with(logger) {
            info("ClientType application service, method updateClientType")
            clientTypeUpdateRequestHandler.updateClientType(id, request)
        }

    override fun activate(id: UUID): ClientTypeActiveStatusResponse =
        with(logger) {
            info("ClientType application service, method activate")
            clientTypeActiveStatusRequestHandler.activate(id)
        }

    override fun deactivate(id: UUID): ClientTypeActiveStatusResponse =
        with(logger) {
            info("ClientType application service, method deactivate")
            clientTypeActiveStatusRequestHandler.deactivate(id)
        }
}
