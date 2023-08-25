package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.MeasurementDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.UnitDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.repositories.UnitJpaRepository
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Measurement
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Unit
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.UnitId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.UnitRepository
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import org.springframework.stereotype.Repository

@Repository
class UnitRepositoryImpl(
    private val jpaRepostiory: UnitJpaRepository
) : UnitRepository {

    override fun getAll() = jpaRepostiory.findAll().map { it.toDomain() }
    override fun get(id: UnitId): Unit? = jpaRepostiory.findById(id.value).map { it.toDomain() }.orElse(null)
    override fun get(ids: List<UnitId>) = jpaRepostiory.findAllById(ids.map { it.value }).map { it.toDomain() }
    override fun get(query: Query<Unit>): Page<Unit> {
        TODO()
    }
    override fun create(entity: Unit) = jpaRepostiory.save(entity.toDatabaseModel()).toDomain()
    override fun createAll(entities: List<Unit>) = jpaRepostiory.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain() }
    override fun update(entity: Unit) = jpaRepostiory.save(entity.toDatabaseModel()).toDomain()
    override fun updateAll(entities: List<Unit>) = jpaRepostiory.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain() }
    override fun delete(id: UnitId) = jpaRepostiory.deleteById(id.value)
    override fun deleteAll(ids: List<UnitId>) = jpaRepostiory.deleteAllById(ids.map { it.value })

    companion object Mappers {
        fun UnitDatabaseModel.toDomain() = Unit(
            id = UnitId(id),
            name = name,
            description = description,
            volume = volume,
            measurement = measurement.toDomain(),
            active = active
        )

        fun MeasurementDatabaseModel.toDomain() = when (this) {
            MeasurementDatabaseModel.CHARACTERS -> Measurement.CHARACTERS
            MeasurementDatabaseModel.POINTS -> Measurement.POINTS
            MeasurementDatabaseModel.HOURS -> Measurement.HOURS
        }

        fun Unit.toDatabaseModel() = UnitDatabaseModel(
            id = id.value,
            name = name,
            description = description,
            volume = volume,
            measurement = measurement.toDatabaseModel(),
            active = active
        )

        fun Measurement.toDatabaseModel() = when (this) {
            Measurement.CHARACTERS -> MeasurementDatabaseModel.CHARACTERS
            Measurement.POINTS -> MeasurementDatabaseModel.POINTS
            Measurement.HOURS -> MeasurementDatabaseModel.HOURS
        }
    }
}
