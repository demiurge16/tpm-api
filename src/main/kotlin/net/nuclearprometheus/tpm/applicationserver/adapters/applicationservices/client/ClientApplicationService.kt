package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers.ClientMapper.toActivityStatus
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers.ClientMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.ClientRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.Client
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientId
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientTypeId
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CountryCode
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client.ClientService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientApplicationService(
    private val service: ClientService,
    private val repository: ClientRepository
) {

    private val logger = loggerFor(this::class.java)

    @Cacheable("clients-cache")
    fun getClients(query: FilteredRequest<Client>) =
        with(logger) {
            info("getClients($query)")
            repository.get(query.toQuery()).map { it.toView() }
        }

    @Cacheable("clients-cache")
    fun getClient(id: UUID) =
        with(logger) {
            info("getClient($id)")

            repository.get(ClientId(id))?.toView() ?: throw NotFoundException("Client with id $id not found")
        }

    @CacheEvict("clients-cache", allEntries = true)
    fun createClient(request: ClientRequest.Create) =
        with(logger) {
            info("createClient($request)")

            service.create(
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
            ).toView()
        }

    @CacheEvict("clients-cache", allEntries = true)
    fun updateClient(id: UUID, request: ClientRequest.Update) =
        with(logger) {
            info("updateClient($id, $request)")

            service.update(
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
            ).toView()
        }

    @CacheEvict("clients-cache", allEntries = true)
    fun activateClient(id: UUID) =
        with(logger) {
            info("activateClient($id)")

            service.activate(ClientId(id)).toActivityStatus()
        }

    @CacheEvict("clients-cache", allEntries = true)
    fun deactivateClient(id: UUID) =
        with(logger) {
            info("deactivateClient($id)")

            service.deactivate(ClientId(id)).toActivityStatus()
        }
}
