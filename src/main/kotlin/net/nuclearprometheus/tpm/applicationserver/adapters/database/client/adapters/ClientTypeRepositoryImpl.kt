package net.nuclearprometheus.tpm.applicationserver.adapters.database.client.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.database.client.entities.ClientTypeDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.client.repositories.ClientTypeJpaRepository
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientType
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientTypeId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientTypeRepository
import org.springframework.stereotype.Repository

@Repository
class ClientTypeRepositoryImpl(private var jpaRepository: ClientTypeJpaRepository) : ClientTypeRepository {

    override fun getAll() = jpaRepository.findAll().map { it.toDomain() }
    override fun get(id: ClientTypeId): ClientType? = jpaRepository.findById(id.value).map { it.toDomain() }.orElse(null)
    override fun get(ids: List<ClientTypeId>) = jpaRepository.findAllById(ids.map { it.value }).map { it.toDomain() }
    override fun create(entity: ClientType) = jpaRepository.save(entity.toDatabaseModel()).toDomain()
    override fun createAll(entities: List<ClientType>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain() }
    override fun update(entity: ClientType) = jpaRepository.save(entity.toDatabaseModel()).toDomain()
    override fun updateAll(entities: List<ClientType>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain() }
    override fun delete(id: ClientTypeId) = jpaRepository.deleteById(id.value)
    override fun deleteAll(ids: List<ClientTypeId>) = jpaRepository.deleteAllById(ids.map { it.value })

    companion object Mapping {
        fun ClientTypeDatabaseModel.toDomain() = ClientType(
            id = ClientTypeId(id),
            name = name,
            description = description,
            corporate = corporate,
            active = active
        )

        fun ClientType.toDatabaseModel() = ClientTypeDatabaseModel(
            id = id.value,
            name = name,
            description = description,
            corporate = corporate,
            active = active
        )
    }
}
