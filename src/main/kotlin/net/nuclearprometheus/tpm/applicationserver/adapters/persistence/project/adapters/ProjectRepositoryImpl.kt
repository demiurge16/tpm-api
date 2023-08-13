package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.client.adapters.ClientRepositoryImpl.Mapping.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.client.adapters.ClientRepositoryImpl.Mapping.toDomain
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters.AccuracyRepositoryImpl.Mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters.AccuracyRepositoryImpl.Mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters.IndustryRepositoryImpl.Mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters.IndustryRepositoryImpl.Mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters.UnitRepositoryImpl.Mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters.UnitRepositoryImpl.Mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.entities.ProjectDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.entities.ProjectStatusDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.repositories.ProjectJpaRepository
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.UnknownCurrency
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.UnknownLanguage
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectStatus
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.CountryRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.CurrencyRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.LanguageRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.expense.ExpenseRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.file.FileRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.task.TaskRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ThreadRepository
import org.springframework.stereotype.Repository

@Repository
class ProjectRepositoryImpl(
    private val projectJpaRepository: ProjectJpaRepository,
    private val languageRepository: LanguageRepository,
    private val currencyRepository: CurrencyRepository,
    private val countryRepository: CountryRepository,
    private val teamMemberRepository: TeamMemberRepository,
    private val taskRepository: TaskRepository,
    private val expenseRepository: ExpenseRepository,
    private val fileRepository: FileRepository,
    private val threadRepository: ThreadRepository
) : ProjectRepository {

    override fun getAll() = projectJpaRepository.findAll()
        .map {
            it.toDomain(
                languageRepository,
                currencyRepository,
                countryRepository,
                teamMemberRepository,
                taskRepository,
                expenseRepository,
                fileRepository
            )
        }

    override fun get(id: ProjectId): Project? = projectJpaRepository.findById(id.value)
        .map {
            it.toDomain(
                languageRepository,
                currencyRepository,
                countryRepository,
                teamMemberRepository,
                taskRepository,
                expenseRepository,
                fileRepository,
            )
        }
        .orElse(null)

    override fun get(ids: List<ProjectId>) = projectJpaRepository.findAllById(ids.map { it.value })
        .map {
            it.toDomain(
                languageRepository,
                currencyRepository,
                countryRepository,
                teamMemberRepository,
                taskRepository,
                expenseRepository,
                fileRepository,
            )
        }

    override fun create(entity: Project): Project {
        val project = projectJpaRepository.save(entity.toDatabaseModel())
        val teamMembers = teamMemberRepository.createAll(entity.teamMembers)
        val tasks = taskRepository.createAll(entity.tasks)
        val expenses = expenseRepository.createAll(entity.expenses)
        val files = fileRepository.createAll(entity.files)
        val threads = threadRepository.createAll(entity.threads)

        return Project(
            id = ProjectId(project.id),
            title = project.title,
            description = project.description,
            sourceLanguage = LanguageCode(project.sourceLanguage)
                .let { languageRepository.get(it) ?: UnknownLanguage(it) },
            targetLanguages = project.targetLanguages
                .map { LanguageCode(it) }
                .map { languageRepository.get(it) ?: UnknownLanguage(it) },
            accuracy = project.accuracy.toDomain(),
            industry = project.industry.toDomain(),
            unit = project.unit.toDomain(),
            amount = project.amount,
            expectedStart = project.expectedStart,
            internalDeadline = project.internalDeadline,
            externalDeadline = project.externalDeadline,
            budget = project.budget,
            currency = CurrencyCode(project.currency)
                .let { currencyRepository.get(it) ?: UnknownCurrency(it) },
            status = project.status.toDomain(),
            teamMembers = teamMembers,
            tasks = tasks,
            expenses = expenses,
            files = files,
            threads = threads,
            client = project.client.toDomain(countryRepository)
        )
    }

    override fun createAll(entities: List<Project>) = entities.map { create(it) }

    override fun update(entity: Project): Project {
        val project = projectJpaRepository.save(entity.toDatabaseModel())

        return Project(
            id = ProjectId(project.id),
            title = project.title,
            description = project.description,
            sourceLanguage = LanguageCode(project.sourceLanguage)
                .let { languageRepository.get(it) ?: UnknownLanguage(it) },
            targetLanguages = project.targetLanguages
                .map { LanguageCode(it) }
                .map { languageRepository.get(it) ?: UnknownLanguage(it) },
            accuracy = project.accuracy.toDomain(),
            industry = project.industry.toDomain(),
            unit = project.unit.toDomain(),
            amount = project.amount,
            expectedStart = project.expectedStart,
            internalDeadline = project.internalDeadline,
            externalDeadline = project.externalDeadline,
            budget = project.budget,
            currency = CurrencyCode(project.currency)
                .let { currencyRepository.get(it) ?: UnknownCurrency(it) },
            status = project.status.toDomain(),
            teamMembers = teamMemberRepository.getAllByProjectId(ProjectId(project.id)),
            tasks = taskRepository.getAllByProjectId(ProjectId(project.id)),
            expenses = expenseRepository.getAllByProjectId(ProjectId(project.id)),
            files = fileRepository.getAllByProjectId(ProjectId(project.id)),
            threads = threadRepository.getAllByProjectId(ProjectId(project.id)),
            client = project.client.toDomain(countryRepository)
        )
    }

    override fun updateAll(entities: List<Project>) = entities.map { update(it) }

    override fun delete(id: ProjectId) {
        fileRepository.deleteAllByProjectId(id)
        expenseRepository.deleteAllByProjectId(id)
        taskRepository.deleteAllByProjectId(id)
        teamMemberRepository.deleteAllByProjectId(id)

        projectJpaRepository.deleteById(id.value)
    }

    override fun deleteAll(ids: List<ProjectId>) = ids.forEach { delete(it) }

    companion object Mappers {

        fun ProjectDatabaseModel.toDomain(
            languageRepository: LanguageRepository,
            currencyRepository: CurrencyRepository,
            countryRepository: CountryRepository,
            teamMemberRepository: TeamMemberRepository,
            taskRepository: TaskRepository,
            expenseRepository: ExpenseRepository,
            fileRepository: FileRepository,
        ) = Project(
            id = ProjectId(id),
            title = title,
            description = description,
            sourceLanguage = LanguageCode(sourceLanguage)
                .let { languageRepository.get(it) ?: UnknownLanguage(it) },
            targetLanguages = targetLanguages
                .map { LanguageCode(it) }
                .map { languageRepository.get(it) ?: UnknownLanguage(it) },
            accuracy = accuracy.toDomain(),
            industry = industry.toDomain(),
            unit = unit.toDomain(),
            amount = amount,
            expectedStart = expectedStart,
            internalDeadline = internalDeadline,
            externalDeadline = externalDeadline,
            budget = budget,
            currency = CurrencyCode(currency)
                .let { currencyRepository.get(it) ?: UnknownCurrency(it) },
            status = status.toDomain(),
            teamMembers = teamMemberRepository.getAllByProjectId(ProjectId(id)),
            tasks = taskRepository.getAllByProjectId(ProjectId(id)),
            expenses = expenseRepository.getAllByProjectId(ProjectId(id)),
            files = fileRepository.getAllByProjectId(ProjectId(id)),
            client = client.toDomain(countryRepository)
        )

        fun Project.toDatabaseModel() = ProjectDatabaseModel(
            id = id.value,
            title = title,
            description = description,
            sourceLanguage = sourceLanguage.id.value,
            targetLanguages = targetLanguages.map { it.id.value },
            accuracy = accuracy.toDatabaseModel(),
            industry = industry.toDatabaseModel(),
            unit = unit.toDatabaseModel(),
            amount = amount,
            expectedStart = expectedStart,
            internalDeadline = internalDeadline,
            externalDeadline = externalDeadline,
            budget = budget,
            currency = currency.id.value,
            status = status.toDatabaseModel(),
            client = client.toDatabaseModel()
        )

        fun ProjectStatusDatabaseModel.toDomain() = when (this) {
            ProjectStatusDatabaseModel.DRAFT -> ProjectStatus.DRAFT
            ProjectStatusDatabaseModel.READY_TO_START -> ProjectStatus.READY_TO_START
            ProjectStatusDatabaseModel.ACTIVE -> ProjectStatus.ACTIVE
            ProjectStatusDatabaseModel.ON_HOLD -> ProjectStatus.ON_HOLD
            ProjectStatusDatabaseModel.READY_TO_DELIVER -> ProjectStatus.READY_TO_DELIVER
            ProjectStatusDatabaseModel.DELIVERED -> ProjectStatus.DELIVERED
            ProjectStatusDatabaseModel.CANCELLED -> ProjectStatus.CANCELLED
            ProjectStatusDatabaseModel.INVOICED -> ProjectStatus.INVOICED
            ProjectStatusDatabaseModel.PAID -> ProjectStatus.PAID
        }

        fun ProjectStatus.toDatabaseModel() = when (this) {
            ProjectStatus.DRAFT -> ProjectStatusDatabaseModel.DRAFT
            ProjectStatus.READY_TO_START -> ProjectStatusDatabaseModel.READY_TO_START
            ProjectStatus.ACTIVE -> ProjectStatusDatabaseModel.ACTIVE
            ProjectStatus.ON_HOLD -> ProjectStatusDatabaseModel.ON_HOLD
            ProjectStatus.READY_TO_DELIVER -> ProjectStatusDatabaseModel.READY_TO_DELIVER
            ProjectStatus.DELIVERED -> ProjectStatusDatabaseModel.DELIVERED
            ProjectStatus.CANCELLED -> ProjectStatusDatabaseModel.CANCELLED
            ProjectStatus.INVOICED -> ProjectStatusDatabaseModel.INVOICED
            ProjectStatus.PAID -> ProjectStatusDatabaseModel.PAID
        }
    }
}
