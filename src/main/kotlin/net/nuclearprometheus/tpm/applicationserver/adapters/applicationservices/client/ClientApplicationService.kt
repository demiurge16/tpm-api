package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers.ClientMapper.toActivityStatus
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers.ClientMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.CreateClient
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.UpdateClient
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientStatus
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.Client as ClientResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.Client
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientId
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientTypeId
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CountryCode
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client.ClientService
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(propagation = Propagation.REQUIRED)
class ClientApplicationService(
    private val service: ClientService,
    private val repository: ClientRepository,
    private val specificationBuilder: SpecificationBuilder<Client>
) {

    private val logger = loggerFor(this::class.java)

    @Cacheable("clients-cache")
    fun getClients(query: FilteredRequest<Client>): Page<ClientResponse> {
        logger.info("getClients($query)")
        return repository.get(query.toQuery(specificationBuilder)).map { it.toView() }
    }

    @Cacheable("clients-cache")
    fun getClient(id: UUID): ClientResponse {
        logger.info("getClient($id)")
        return repository.get(ClientId(id))?.toView() ?: throw NotFoundException("Client with id $id not found")
    }

    @CacheEvict("clients-cache", allEntries = true)
    fun createClient(request: CreateClient): ClientResponse {
        logger.info("createClient($request)")
        val client = service.create(
            request.name,
            request.email,
            request.phone,
            request.address,
            request.city,
            request.state,
            request.zip,
            CountryCode(request.countryCode),
            request.vat,
            request.notes,
            ClientTypeId(request.clientTypeId)
        )
        return client.toView()
    }

    @CacheEvict("clients-cache", allEntries = true)
    fun updateClient(id: UUID, request: UpdateClient): ClientResponse {
        logger.info("updateClient($id, $request)")
        val client = service.update(
            ClientId(id),
            request.name,
            request.email,
            request.phone,
            request.address,
            request.city,
            request.state,
            request.zip,
            CountryCode(request.countryCode),
            request.vat,
            request.notes,
            ClientTypeId(request.clientTypeId)
        )
        return client.toView()
    }

    @CacheEvict("clients-cache", allEntries = true)
    fun activateClient(id: UUID): ClientStatus {
        logger.info("activateClient($id)")
        return service.activate(ClientId(id)).toActivityStatus()
    }

    @CacheEvict("clients-cache", allEntries = true)
    fun deactivateClient(id: UUID): ClientStatus {
        logger.info("deactivateClient($id)")
        return service.deactivate(ClientId(id)).toActivityStatus()
    }
}
