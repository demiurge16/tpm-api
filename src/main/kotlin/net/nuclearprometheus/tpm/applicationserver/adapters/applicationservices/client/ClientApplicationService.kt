package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers.ClientActiveStatusRequestHandler
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers.ClientCreateRequestHandler
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers.ClientUpdateRequestHandler
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers.ClientViewRequestHandler
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.ClientCreateRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.ClientUpdateRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientActiveStatusResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.Client
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientApplicationService(
    private val clientTypeViewRequestHandler: ClientViewRequestHandler,
    private val clientTypeCreateRequestHandler: ClientCreateRequestHandler,
    private val clientTypeUpdateRequestHandler: ClientUpdateRequestHandler,
    private val clientTypeActiveStatusRequestHandler: ClientActiveStatusRequestHandler
) {

    private val logger = loggerFor(this::class.java)

    fun getClients(query: FilteredRequest<Client>) =
        with(logger) {
            info("Client application service, method getClients")
            info("Query: $query")

            clientTypeViewRequestHandler.getClients(query)
        }

    fun getClient(id: UUID) =
        with(logger) {
            info("Client application service, method getClient")
            info("Id: $id")

            clientTypeViewRequestHandler.getClient(id)
        }

    fun createClient(request: ClientCreateRequest) =
        with(logger) {
            info("Client application service, method createClient")
            info("Request: $request")

            clientTypeCreateRequestHandler.create(request)
        }

    fun updateClient(id: UUID, request: ClientUpdateRequest) =
        with(logger) {
            info("Client application service, method updateClient")
            info("Id: $id")
            info("Request: $request")

            clientTypeUpdateRequestHandler.update(id, request)
        }

    fun activate(id: UUID): ClientActiveStatusResponse =
        with(logger) {
            info("Client application service, method activate")
            info("Id: $id")

            clientTypeActiveStatusRequestHandler.activate(id)
        }

    fun deactivate(id: UUID): ClientActiveStatusResponse =
        with(logger) {
            info("Client application service, method deactivate")
            info("Id: $id")

            clientTypeActiveStatusRequestHandler.deactivate(id)
        }
}
