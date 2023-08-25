package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.PriorityDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.repositories.PriorityJpaRepository
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Priority
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.PriorityId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.PriorityRepository
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import org.springframework.stereotype.Repository

@Repository
class PriorityRepositoryImpl(
    private val jpaRepository: PriorityJpaRepository
) : PriorityRepository {

    override fun getAll() = jpaRepository.findAll().map { it.toDomain() }
    override fun get(id: PriorityId): Priority? = jpaRepository.findById(id.value).map { it.toDomain() }.orElse(null)
    override fun get(ids: List<PriorityId>): List<Priority> = jpaRepository.findAllById(ids.map { it.value }).map { it.toDomain() }
    override fun get(query: Query<Priority>): Page<Priority> {
        TODO("Not yet implemented")
    }
    override fun create(entity: Priority) = jpaRepository.save(entity.toDatabaseModel()).toDomain()
    override fun createAll(entities: List<Priority>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain() }
    override fun update(entity: Priority) = jpaRepository.save(entity.toDatabaseModel()).toDomain()
    override fun updateAll(entities: List<Priority>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain() }
    override fun delete(id: PriorityId) = jpaRepository.deleteById(id.value)
    override fun deleteAll(ids: List<PriorityId>) = jpaRepository.deleteAllById(ids.map { it.value })

    companion object Mappers {
        fun PriorityDatabaseModel.toDomain() = Priority(
            id = PriorityId(id),
            name = name,
            description = description,
            emoji = emoji,
            value = value,
            active = active
        )

        fun Priority.toDatabaseModel() = PriorityDatabaseModel(
            id = id.value,
            name = name,
            description = description,
            emoji = emoji,
            value = value,
            active = active
        )
    }
}