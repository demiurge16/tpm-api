package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.ServiceTypeDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.repositories.ServiceTypeJpaRepository
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ServiceType
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ServiceTypeId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.ServiceTypeRepository
import org.springframework.stereotype.Repository

@Repository
class ServiceTypeRepositoryImpl(
    private val jpaRepository: ServiceTypeJpaRepository
) : ServiceTypeRepository {

    override fun getAll() = jpaRepository.findAll().map { it.toDomain() }
    override fun get(id: ServiceTypeId): ServiceType? = jpaRepository.findById(id.value).map { it.toDomain() }.orElse(null)
    override fun get(ids: List<ServiceTypeId>) = jpaRepository.findAllById(ids.map { it.value }).map { it.toDomain() }
    override fun create(entity: ServiceType) = jpaRepository.save(entity.toDatabaseModel()).toDomain()
    override fun createAll(entities: List<ServiceType>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain() }
    override fun update(entity: ServiceType) = jpaRepository.save(entity.toDatabaseModel()).toDomain()
    override fun updateAll(entities: List<ServiceType>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain() }
    override fun delete(id: ServiceTypeId) = jpaRepository.deleteById(id.value)
    override fun deleteAll(ids: List<ServiceTypeId>) = jpaRepository.deleteAllById(ids.map { it.value })

    companion object Mappers {
        fun ServiceTypeDatabaseModel.toDomain() = ServiceType(
            id = ServiceTypeId(id),
            name = name,
            description = description,
            active = active
        )

        fun ServiceType.toDatabaseModel() = ServiceTypeDatabaseModel(
            id = id.value,
            name = name,
            description = description,
            active = active
        )
    }
}