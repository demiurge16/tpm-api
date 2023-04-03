package net.nuclearprometheus.tpm.applicationserver.adapters.database.dictionaries.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.database.dictionaries.entities.AccuracyDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.dictionaries.repositories.AccuracyJpaRepository
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Accuracy
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.AccuracyId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.AccuracyRepository
import org.springframework.stereotype.Repository

@Repository
class AccuracyRepositoryImpl(
    private val jpaRepository: AccuracyJpaRepository
) : AccuracyRepository {

    override fun getAll() = jpaRepository.findAll().map { it.toDomain() }
    override fun get(id: AccuracyId): Accuracy? = jpaRepository.findById(id.value).map { it.toDomain() }.orElse(null)
    override fun get(ids: List<AccuracyId>) = jpaRepository.findAllById(ids.map { it.value }).map { it.toDomain() }
    override fun create(entity: Accuracy) = jpaRepository.save(entity.toDatabaseModel()).toDomain()
    override fun createAll(entities: List<Accuracy>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain() }
    override fun update(entity: Accuracy) = jpaRepository.save(entity.toDatabaseModel()).toDomain()
    override fun updateAll(entities: List<Accuracy>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain() }
    override fun delete(id: AccuracyId) = jpaRepository.deleteById(id.value)
    override fun deleteAll(ids: List<AccuracyId>) = jpaRepository.deleteAllById(ids.map { it.value })

    companion object Mappers {
        fun AccuracyDatabaseModel.toDomain() = Accuracy(
            id = AccuracyId(id),
            name = name,
            description = description,
            active = active
        )

        fun Accuracy.toDatabaseModel() = AccuracyDatabaseModel(
            id = id.value,
            name = name,
            description = description,
            active = active
        )
    }
}
