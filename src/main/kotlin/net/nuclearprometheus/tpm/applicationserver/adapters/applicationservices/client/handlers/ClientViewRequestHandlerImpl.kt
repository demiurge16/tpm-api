package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.applyQuery
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.Client
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientRepository
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientViewRequestHandlerImpl(private val repository: ClientRepository) : ClientViewRequestHandler {

    private val logger = loggerFor(this::class.java)

    @Cacheable("clients-cache")
    override fun getClients(query: FilteredRequest<Client>) =
        with(logger) {
            info("Client view request handler, method getClients")
            info("Query: $query")

            repository.getAll().applyQuery(query).map { it.toView() }
        }

    @Cacheable("clients-cache")
    override fun getClient(id: UUID) =
        with(logger) {
            info("Client view request handler, method getClient")
            info("Id: $id")

            repository.get(ClientId(id))?.toView() ?: throw NotFoundException("Client with id $id not found")
        }
}