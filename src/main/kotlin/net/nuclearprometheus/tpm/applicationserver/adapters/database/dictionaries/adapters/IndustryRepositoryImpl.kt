package net.nuclearprometheus.tpm.applicationserver.adapters.database.dictionaries.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.database.dictionaries.entities.IndustryDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.dictionaries.repositories.IndustryJpaRepository
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Industry
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.IndustryId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.IndustryRepository
import org.springframework.stereotype.Repository

@Repository
class IndustryRepositoryImpl(
    private val jpaRepository: IndustryJpaRepository
) : IndustryRepository {

    override fun getAll() = jpaRepository.findAll().map { it.toDomain() }
    override fun get(id: IndustryId): Industry? = jpaRepository.findById(id.value).map { it.toDomain() }.orElse(null)
    override fun get(ids: List<IndustryId>) = jpaRepository.findAllById(ids.map { it.value }).map { it.toDomain() }
    override fun create(entity: Industry) = jpaRepository.save(entity.toDatabaseModel()).toDomain()
    override fun createAll(entities: List<Industry>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain() }
    override fun update(entity: Industry) = jpaRepository.save(entity.toDatabaseModel()).toDomain()
    override fun updateAll(entities: List<Industry>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain() }
    override fun delete(id: IndustryId) = jpaRepository.deleteById(id.value)
    override fun deleteAll(ids: List<IndustryId>) = jpaRepository.deleteAllById(ids.map { it.value })

    companion object Mappers {
        fun IndustryDatabaseModel.toDomain() = Industry(
            id = IndustryId(id),
            name = name,
            description = description,
            active = active
        )

        fun Industry.toDatabaseModel() = IndustryDatabaseModel(
            id = id.value,
            name = name,
            description = description,
            active = active
        )
    }
}