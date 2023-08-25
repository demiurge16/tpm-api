package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.toPageable
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.MeasurementDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.UnitDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.repositories.UnitJpaRepository
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.specifications.UnitSpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Measurement
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Unit
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.UnitId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.UnitRepository
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import org.springframework.stereotype.Repository

@Repository
class UnitRepositoryImpl(
    private val jpaRepository: UnitJpaRepository,
    private val specificationBuilder: UnitSpecificationBuilder
) : UnitRepository {

    override fun getAll() = jpaRepository.findAll().map { it.toDomain() }
    override fun get(id: UnitId): Unit? = jpaRepository.findById(id.value).map { it.toDomain() }.orElse(null)
    override fun get(ids: List<UnitId>) = jpaRepository.findAllById(ids.map { it.value }).map { it.toDomain() }
    override fun get(query: Query<Unit>): Page<Unit> {
        val page = jpaRepository.findAll(specificationBuilder.build(query), query.toPageable())
        return Page(
            items = page.content.map { it.toDomain() },
            currentPage = page.number,
            totalPages = page.totalPages,
            totalItems = page.totalElements
        )
    }
    override fun create(entity: Unit) = jpaRepository.save(entity.toDatabaseModel()).toDomain()
    override fun createAll(entities: List<Unit>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain() }
    override fun update(entity: Unit) = jpaRepository.save(entity.toDatabaseModel()).toDomain()
    override fun updateAll(entities: List<Unit>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain() }
    override fun delete(id: UnitId) = jpaRepository.deleteById(id.value)
    override fun deleteAll(ids: List<UnitId>) = jpaRepository.deleteAllById(ids.map { it.value })

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
