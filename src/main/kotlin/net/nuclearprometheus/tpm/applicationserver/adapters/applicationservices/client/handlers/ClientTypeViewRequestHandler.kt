package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.applyQuery
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientType
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientTypeId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientTypeRepository
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientTypeViewRequestHandler(private val repository: ClientTypeRepository) {

    private val logger = loggerFor(this::class.java)

    @Cacheable("client-types-cache")
    fun getClientTypes(query: FilteredRequest<ClientType>) =
        with(logger) {
            info("ClientType view request handler, method getClientTypes")
            info("Query: $query")

            repository.getAll().applyQuery(query).map { it.toView() }
        }

    @Cacheable("client-types-cache")
    fun getClientType(id: UUID) =
        with(logger) {
            info("ClientType view request handler, method getClientType")
            info("Id: $id")

            repository.get(ClientTypeId(id))?.toView() ?: throw NotFoundException("Client type with id $id not found")
        }
}