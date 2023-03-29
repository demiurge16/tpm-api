package net.nuclearprometheus.tpm.applicationserver.adapters.database.client.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.database.client.mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.client.mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.adapters.database.client.repositories.ClientTypeJpaRepository
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientType
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientTypeId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientTypeRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ClientTypeRepositoryImpl(private var jpaRepository: ClientTypeJpaRepository) : ClientTypeRepository {

    override fun getAll() = jpaRepository.findAll().map { it.toDomain() }
    override fun get(id: ClientTypeId): ClientType? = jpaRepository.findById(id.value).map { it.toDomain() }.orElse(null)
    override fun get(ids: List<ClientTypeId>) = jpaRepository.findAllById(ids.map { it.value }).map { it.toDomain() }
    override fun create(clientType: ClientType) = jpaRepository.save(clientType.toDatabaseModel()).toDomain()
    override fun update(clientType: ClientType) = jpaRepository.save(clientType.toDatabaseModel()).toDomain()
    override fun delete(id: ClientTypeId) = jpaRepository.deleteById(id.value)
}
