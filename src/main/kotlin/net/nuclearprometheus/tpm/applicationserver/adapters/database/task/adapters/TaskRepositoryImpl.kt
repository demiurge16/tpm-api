package net.nuclearprometheus.tpm.applicationserver.adapters.database.task.adapters

import TaskStatusDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.dictionaries.adapters.AccuracyRepositoryImpl.Mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.dictionaries.adapters.AccuracyRepositoryImpl.Mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.adapters.database.dictionaries.adapters.IndustryRepositoryImpl.Mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.dictionaries.adapters.IndustryRepositoryImpl.Mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.adapters.database.dictionaries.adapters.PriorityRepositoryImpl.Mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.dictionaries.adapters.PriorityRepositoryImpl.Mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.adapters.database.dictionaries.adapters.UnitRepositoryImpl.Mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.dictionaries.adapters.UnitRepositoryImpl.Mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.adapters.database.task.entities.TaskDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.task.repositories.TaskJpaRepository
import net.nuclearprometheus.tpm.applicationserver.adapters.database.teammember.adapters.TeamMemberRepositoryImpl.Mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.teammember.adapters.TeamMemberRepositoryImpl.Mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.UnknownCurrency
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.UnknownLanguage
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskId
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskStatus
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.CurrencyRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.LanguageRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.task.TaskRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import org.springframework.stereotype.Repository

@Repository
class TaskRepositoryImpl(
    private val jpaRepository: TaskJpaRepository,
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
            amount = amount,
            expectedStart = expectedStart,
            deadline = deadline,
            budget = budget,
            currency = with(CurrencyCode(currency)) {
                currencyRepository.get(this) ?: UnknownCurrency(this)
            },
            status = status.toDomain(),
            priority = priority.toDomain(),
            assignee = assignee?.toDomain(userRepository),
            projectId = ProjectId(projectId)
        )

        fun Task.toDatabaseModel() = TaskDatabaseModel(
            id = id.value,
            title = title,
            description = description,
            sourceLanguage = sourceLanguage.code.value,
            targetLanguage = targetLanguage.code.value,
            accuracy = accuracy.toDatabaseModel(),
            industry = industry.toDatabaseModel(),
            unit = unit.toDatabaseModel(),
            amount = amount,
            expectedStart = expectedStart,
            deadline = deadline,
            budget = budget,
            currency = currency.code.value,
            status = status.toDatabaseModel(),
            priority = priority.toDatabaseModel(),
            assignee = assignee?.toDatabaseModel(),
            projectId = projectId.value
        )

        fun TaskStatusDatabaseModel.toDomain() = when (this) {
            TaskStatusDatabaseModel.DRAFT -> TaskStatus.DRAFT
            TaskStatusDatabaseModel.ASSIGNED -> TaskStatus.ASSIGNED
            TaskStatusDatabaseModel.IN_PROGRESS -> TaskStatus.IN_PROGRESS
            TaskStatusDatabaseModel.NEEDS_REVIEW -> TaskStatus.NEEDS_REVIEW
            TaskStatusDatabaseModel.REVISIONS_NEEDED -> TaskStatus.REVISIONS_NEEDED
            TaskStatusDatabaseModel.COMPLETED -> TaskStatus.COMPLETED
            TaskStatusDatabaseModel.CANCELLED -> TaskStatus.CANCELLED
        }

        fun TaskStatus.toDatabaseModel() = when (this) {
            TaskStatus.DRAFT -> TaskStatusDatabaseModel.DRAFT
            TaskStatus.ASSIGNED -> TaskStatusDatabaseModel.ASSIGNED
            TaskStatus.IN_PROGRESS -> TaskStatusDatabaseModel.IN_PROGRESS
            TaskStatus.NEEDS_REVIEW -> TaskStatusDatabaseModel.NEEDS_REVIEW
            TaskStatus.REVISIONS_NEEDED -> TaskStatusDatabaseModel.REVISIONS_NEEDED
            TaskStatus.COMPLETED -> TaskStatusDatabaseModel.COMPLETED
            TaskStatus.CANCELLED -> TaskStatusDatabaseModel.CANCELLED
        }
    }
}
