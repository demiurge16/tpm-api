package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers.ClientTypeMapper.toActivityStatus
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers.ClientTypeMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.CreateClientType
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.UpdateClientType
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientType
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientTypeId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientTypeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client.ClientTypeService
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(propagation = Propagation.REQUIRED)
class ClientTypeApplicationService(
    private val service: ClientTypeService,
    private val repository: ClientTypeRepository,
    private val specificationBuilder: SpecificationBuilder<ClientType>
) {

    private val logger = loggerFor(this::class.java)

    @Cacheable("client-types-cache")
    fun getClientTypes(query: FilteredRequest<ClientType>) =
        with(logger) {
            info("getClientTypes($query)")
            repository.get(query.toQuery(specificationBuilder)).map { it.toView() }
        }

    @Cacheable("client-types-cache")
    fun getClientType(id: UUID) =
        with(logger) {
            info("getClientType($id)")

            repository.get(ClientTypeId(id))?.toView() ?: throw NotFoundException("Client type with id $id not found")
        }

    @CacheEvict(value = ["client-types-cache"], allEntries = true)
    fun createClientType(request: CreateClientType) =
        with(logger) {
            info("createClientType($request)")

            service.create(request.name, request.description, request.corporate).toView()
        }

    @CacheEvict(value = ["client-types-cache"], allEntries = true)
    fun updateClientType(id: UUID, request: UpdateClientType) =
        with(logger) {
            info("updateClientType($id, $request)")

            service.update(ClientTypeId(id), request.name, request.description, request.corporate).toView()
        }

    @CacheEvict(value = ["client-types-cache"], allEntries = true)
    fun activateClientType(id: UUID) =
        with(logger) {
            info("activateClientType($id)")

            service.activate(ClientTypeId(id)).toActivityStatus()
        }

    @CacheEvict(value = ["client-types-cache"], allEntries = true)
    fun deactivateClientType(id: UUID) =
        with(logger) {
            info("deactivateClientType($id)")

            service.deactivate(ClientTypeId(id)).toActivityStatus()
        }
}
