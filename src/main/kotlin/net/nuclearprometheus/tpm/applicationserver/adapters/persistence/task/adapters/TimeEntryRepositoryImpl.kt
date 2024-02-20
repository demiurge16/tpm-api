package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.toPageable
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.entities.TimeEntryDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.entities.TimeEntryStatusDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.entities.TimeUnitDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.repositories.TimeEntryJpaRepository
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.specifications.TimeEntrySpecificationFactory
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.specifications.TimeEntryTaskSpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.task.TimeEntryRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import org.springframework.stereotype.Repository

@Repository
class TimeEntryRepositoryImpl(
    private val jpaRepository: TimeEntryJpaRepository,
    private val userRepository: UserRepository,
    private val specificationBuilder: TimeEntrySpecificationFactory,
    private val taskSpecificationBuilder: TimeEntryTaskSpecificationBuilder
) : TimeEntryRepository {
    override fun getAll() = jpaRepository.findAll().map { it.toDomain(userRepository) }
    override fun get(id: TimeEntryId) = jpaRepository.findById(id.value).map { it.toDomain(userRepository) }.orElse(null)
    override fun get(ids: List<TimeEntryId>) = jpaRepository.findAllById(ids.map { it.value }).map { it.toDomain(userRepository) }
    override fun get(query: Query<TimeEntry>): Page<TimeEntry> {
        val page = jpaRepository.findAll(specificationBuilder.create(query), query.toPageable())
        return Page(
            items = page.content.map { it.toDomain(userRepository) },
            currentPage = page.number,
            totalPages = page.totalPages,
            totalItems = page.totalElements
        )
    }
    override fun getForTask(taskId: TaskId, query: Query<TimeEntry>): Page<TimeEntry> {
        val specification = specificationBuilder.create(query)
            .and(taskSpecificationBuilder.create(taskId))
        val page = jpaRepository.findAll(specification, query.toPageable())
        return Page(
            items = page.content.map { it.toDomain(userRepository) },
            currentPage = page.number,
            totalPages = page.totalPages,
            totalItems = page.totalElements
        )
    }

    override fun create(entity: TimeEntry) = jpaRepository.save(entity.toDatabaseModel()).toDomain(userRepository)
    override fun createAll(entities: List<TimeEntry>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain(userRepository) }
    override fun update(entity: TimeEntry) = jpaRepository.save(entity.toDatabaseModel()).toDomain(userRepository)
    override fun updateAll(entities: List<TimeEntry>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain(userRepository) }
    override fun delete(id: TimeEntryId) = jpaRepository.deleteById(id.value)
    override fun deleteAll(ids: List<TimeEntryId>) = jpaRepository.deleteAllById(ids.map { it.value })

    companion object Mappers {

        fun TimeEntryDatabaseModel.toDomain(userRepository: UserRepository) = TimeEntry(
            id = TimeEntryId(id),
            user = userRepository.get(UserId(userId)) ?: throw IllegalStateException("User not found"),
            date = date,
            timeSpent = timeSpent,
            timeUnit = timeUnit.toDomain(),
            status = status.toDomain(),
            description = description,
            taskId = TaskId(taskId)
        )

        fun TimeEntry.toDatabaseModel() = TimeEntryDatabaseModel(
            id = id.value,
            userId = user.id.value,
            date = date,
            timeSpent = timeSpent,
            timeUnit = timeUnit.toDatabaseModel(),
            status = status.toDatabaseModel(),
            description = description,
            taskId = taskId.value
        )

        fun TimeUnitDatabaseModel.toDomain() = when (this) {
            TimeUnitDatabaseModel.MINUTES -> TimeUnit.MINUTES
            TimeUnitDatabaseModel.HOURS -> TimeUnit.HOURS
            TimeUnitDatabaseModel.DAYS -> TimeUnit.DAYS
        }

        fun TimeUnit.toDatabaseModel() = when (this) {
            TimeUnit.MINUTES -> TimeUnitDatabaseModel.MINUTES
            TimeUnit.HOURS -> TimeUnitDatabaseModel.HOURS
            TimeUnit.DAYS -> TimeUnitDatabaseModel.DAYS
        }

        fun TimeEntryStatusDatabaseModel.toDomain() = when (this) {
            TimeEntryStatusDatabaseModel.DRAFT -> TimeEntryStatus.DRAFT
            TimeEntryStatusDatabaseModel.SUBMITTED -> TimeEntryStatus.SUBMITTED
            TimeEntryStatusDatabaseModel.APPROVED -> TimeEntryStatus.APPROVED
            TimeEntryStatusDatabaseModel.REJECTED -> TimeEntryStatus.REJECTED
        }

        fun TimeEntryStatus.toDatabaseModel() = when (this) {
            TimeEntryStatus.DRAFT -> TimeEntryStatusDatabaseModel.DRAFT
            TimeEntryStatus.SUBMITTED -> TimeEntryStatusDatabaseModel.SUBMITTED
            TimeEntryStatus.APPROVED -> TimeEntryStatusDatabaseModel.APPROVED
            TimeEntryStatus.REJECTED -> TimeEntryStatusDatabaseModel.REJECTED
        }
    }
}