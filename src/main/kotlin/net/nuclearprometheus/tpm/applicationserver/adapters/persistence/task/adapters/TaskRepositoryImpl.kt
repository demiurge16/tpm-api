package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.toPageable
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters.AccuracyRepositoryImpl.Mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters.AccuracyRepositoryImpl.Mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters.IndustryRepositoryImpl.Mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters.IndustryRepositoryImpl.Mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters.PriorityRepositoryImpl.Mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters.PriorityRepositoryImpl.Mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters.ServiceTypeRepositoryImpl.Mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters.ServiceTypeRepositoryImpl.Mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters.UnitRepositoryImpl.Mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters.UnitRepositoryImpl.Mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.entities.TaskDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.entities.TaskStatusDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.repositories.TaskJpaRepository
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.specifications.TaskSpecificationFactory
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Currency.UnknownCurrency
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Language.UnknownLanguage
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskId
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskStatus
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.CurrencyRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.LanguageRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.task.TaskRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class TaskRepositoryImpl(
    private val jpaRepository: TaskJpaRepository,
    private val specificationBuilder: TaskSpecificationFactory,
    private val languageRepository: LanguageRepository,
    private val currencyRepository: CurrencyRepository,
    private val userRepository: UserRepository
) : TaskRepository {

    override fun getAll() = jpaRepository.findAll()
        .map { it.toDomain(languageRepository, currencyRepository, userRepository) }
    override fun get(id: TaskId): Task? = jpaRepository.findById(id.value)
        .map { it.toDomain(languageRepository, currencyRepository, userRepository) }.orElse(null)
    override fun get(ids: List<TaskId>) = jpaRepository.findAllById(ids.map { it.value })
        .map { it.toDomain(languageRepository, currencyRepository, userRepository) }

    override fun get(query: Query<Task>): Page<Task> {
        val page = jpaRepository.findAll(specificationBuilder.create(query), query.toPageable())
        return Page(
            items = page.content.map { it.toDomain(languageRepository, currencyRepository, userRepository) },
            currentPage = page.number,
            totalPages = page.totalPages,
            totalItems = page.totalElements
        )
    }

    override fun create(entity: Task) = jpaRepository.save(entity.toDatabaseModel())
        .toDomain(languageRepository, currencyRepository, userRepository)
    override fun createAll(entities: List<Task>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() })
        .map { it.toDomain(languageRepository, currencyRepository, userRepository) }
    override fun update(entity: Task) = jpaRepository.save(entity.toDatabaseModel())
        .toDomain(languageRepository, currencyRepository, userRepository)
    override fun updateAll(entities: List<Task>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() })
        .map { it.toDomain(languageRepository, currencyRepository, userRepository) }
    override fun delete(id: TaskId) = jpaRepository.deleteById(id.value)
    override fun deleteAll(ids: List<TaskId>) = jpaRepository.deleteAllById(ids.map { it.value })
    override fun getAllByProjectId(projectId: ProjectId) = jpaRepository.findAllByProjectId(projectId.value)
        .map { it.toDomain(languageRepository, currencyRepository, userRepository) }

    override fun getAllByProjectIdAndQuery(projectId: ProjectId, query: Query<Task>): Page<Task> {
        val specification = specificationBuilder.create(query)
            .and { root, _, criteriaBuilder -> criteriaBuilder.equal(root.get<UUID>("projectId"), projectId.value) }
        val page = jpaRepository.findAll(specification, query.toPageable())
        return Page(
            items = page.content.map { it.toDomain(languageRepository, currencyRepository, userRepository) },
            currentPage = page.number,
            totalPages = page.totalPages,
            totalItems = page.totalElements
        )
    }

    override fun deleteAllByProjectId(projectId: ProjectId) = jpaRepository.deleteAllByProjectId(projectId.value)

    companion object Mappers {
        fun TaskDatabaseModel.toDomain(
            languageRepository: LanguageRepository,
            currencyRepository: CurrencyRepository,
            userRepository: UserRepository
        ) = Task(
            id = TaskId(id),
            title = title,
            description = description,
            sourceLanguage = with(LanguageCode(sourceLanguage)) {
                languageRepository.get(this) ?: UnknownLanguage(this)
            },
            targetLanguage = with(LanguageCode(targetLanguage)) {
                languageRepository.get(this) ?: UnknownLanguage(this)
            },
            accuracy = accuracy.toDomain(),
            industry = industry.toDomain(),
            unit = unit.toDomain(),
            serviceType = serviceType.toDomain(),
            amount = amount,
            expectedStart = expectedStart,
            deadline = deadline,
            budget = budget,
            currency = with(CurrencyCode(currency)) {
                currencyRepository.get(this) ?: UnknownCurrency(this)
            },
            status = status.toDomain(),
            priority = priority.toDomain(),
            assignee = with(assigneeId) {
                if (this != null) userRepository.get(UserId(this)) else null
            },
            projectId = ProjectId(projectId)
        )

        fun Task.toDatabaseModel() = TaskDatabaseModel(
            id = id.value,
            title = title,
            description = description,
            sourceLanguage = sourceLanguage.id.value,
            targetLanguage = targetLanguage.id.value,
            accuracy = accuracy.toDatabaseModel(),
            industry = industry.toDatabaseModel(),
            unit = unit.toDatabaseModel(),
            serviceType = serviceType.toDatabaseModel(),
            amount = amount,
            expectedStart = expectedStart,
            deadline = deadline,
            budget = budget,
            currency = currency.id.value,
            status = status.toDatabaseModel(),
            priority = priority.toDatabaseModel(),
            assigneeId = assignee?.id?.value,
            projectId = projectId.value
        )

        fun TaskStatusDatabaseModel.toDomain() = when (this) {
            TaskStatusDatabaseModel.DRAFT -> TaskStatus.DRAFT
            TaskStatusDatabaseModel.ASSIGNED -> TaskStatus.ASSIGNED
            TaskStatusDatabaseModel.IN_PROGRESS -> TaskStatus.IN_PROGRESS
            TaskStatusDatabaseModel.IN_REVIEW -> TaskStatus.IN_REVIEW
            TaskStatusDatabaseModel.ON_HOLD -> TaskStatus.ON_HOLD
            TaskStatusDatabaseModel.READY_TO_DELIVER -> TaskStatus.READY_TO_DELIVER
            TaskStatusDatabaseModel.COMPLETED -> TaskStatus.COMPLETED
            TaskStatusDatabaseModel.CANCELLED -> TaskStatus.CANCELLED
        }

        fun TaskStatus.toDatabaseModel() = when (this) {
            TaskStatus.DRAFT -> TaskStatusDatabaseModel.DRAFT
            TaskStatus.ASSIGNED -> TaskStatusDatabaseModel.ASSIGNED
            TaskStatus.IN_PROGRESS -> TaskStatusDatabaseModel.IN_PROGRESS
            TaskStatus.IN_REVIEW -> TaskStatusDatabaseModel.IN_REVIEW
            TaskStatus.ON_HOLD -> TaskStatusDatabaseModel.ON_HOLD
            TaskStatus.READY_TO_DELIVER -> TaskStatusDatabaseModel.READY_TO_DELIVER
            TaskStatus.COMPLETED -> TaskStatusDatabaseModel.COMPLETED
            TaskStatus.CANCELLED -> TaskStatusDatabaseModel.CANCELLED
        }
    }
}
