package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.client.adapters.ClientRepositoryImpl.Mapping.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.client.adapters.ClientRepositoryImpl.Mapping.toDomain
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.toPageable
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters.AccuracyRepositoryImpl.Mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters.AccuracyRepositoryImpl.Mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters.IndustryRepositoryImpl.Mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters.IndustryRepositoryImpl.Mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters.ServiceTypeRepositoryImpl.Mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters.ServiceTypeRepositoryImpl.Mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters.UnitRepositoryImpl.Mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters.UnitRepositoryImpl.Mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.entities.ProjectDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.entities.ProjectStatusDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.repositories.ProjectJpaRepository
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.specifications.ProjectSpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.specifications.ProjectUserSpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.adapters.TeamMemberRepositoryImpl.Mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.adapters.TeamMemberRepositoryImpl.Mappers.toTeamMembers
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Currency.UnknownCurrency
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Language.UnknownLanguage
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectStatus
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.CountryRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.CurrencyRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.LanguageRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.expense.ExpenseRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.file.FileRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.task.TaskRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ThreadRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import org.springframework.stereotype.Repository

@Repository
class ProjectRepositoryImpl(
    private val jpaRepository: ProjectJpaRepository,
    private val specificationBuilder: ProjectSpecificationBuilder,
    private val userSpecificationBuilder: ProjectUserSpecificationBuilder,
    private val languageRepository: LanguageRepository,
    private val currencyRepository: CurrencyRepository,
    private val countryRepository: CountryRepository,
    private val teamMemberRepository: TeamMemberRepository,
    private val taskRepository: TaskRepository,
    private val expenseRepository: ExpenseRepository,
    private val fileRepository: FileRepository,
    private val userRepository: UserRepository,
    private val threadRepository: ThreadRepository
) : ProjectRepository {

    override fun getAll() = jpaRepository.findAll()
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

    override fun get(id: ProjectId): Project? = jpaRepository.findById(id.value)
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

    override fun get(ids: List<ProjectId>) = jpaRepository.findAllById(ids.map { it.value })
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

    override fun get(query: Query<Project>): Page<Project> {
        val page = jpaRepository.findAll(specificationBuilder.build(query), query.toPageable())
        return Page(
            items = page.content
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
                },
            currentPage = page.number,
            totalPages = page.totalPages,
            totalItems = page.totalElements
        )
    }

    override fun getProjectsForUser(userId: UserId, query: Query<Project>): Page<Project> {
        val specification = specificationBuilder.build(query)
            .and(userSpecificationBuilder.build(userId))
        val page = jpaRepository.findAll(specification, query.toPageable())
        return Page(
            items = page.content
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
                },
            currentPage = page.number,
            totalPages = page.totalPages,
            totalItems = page.totalElements
        )
    }

    override fun create(entity: Project): Project {
        val project = jpaRepository.save(entity.toDatabaseModel())
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
            serviceTypes = project.serviceTypes.map { it.toDomain() },
            amount = project.amount,
            expectedStart = project.expectedStart,
            internalDeadline = project.internalDeadline,
            externalDeadline = project.externalDeadline,
            budget = project.budget,
            currency = CurrencyCode(project.currency)
                .let { currencyRepository.get(it) ?: UnknownCurrency(it) },
            status = project.status.toDomain(),
            teamMembers = project.teamMembers.toTeamMembers(userRepository),
            tasks = tasks,
            expenses = expenses,
            files = files,
            threads = threads,
            client = project.client.toDomain(countryRepository)
        )
    }

    override fun createAll(entities: List<Project>) = entities.map { create(it) }

    override fun update(entity: Project): Project {
        val project = jpaRepository.save(entity.toDatabaseModel())

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
            serviceTypes = project.serviceTypes.map { it.toDomain() },
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

        jpaRepository.deleteById(id.value)
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
            sourceLanguage = LanguageCode(sourceLanguage).let { languageRepository.get(it) ?: UnknownLanguage(it) },
            targetLanguages = targetLanguages
                .map { LanguageCode(it) }
                .map { languageRepository.get(it) ?: UnknownLanguage(it) },
            accuracy = accuracy.toDomain(),
            industry = industry.toDomain(),
            unit = unit.toDomain(),
            serviceTypes = serviceTypes.map { it.toDomain() },
            amount = amount,
            expectedStart = expectedStart,
            internalDeadline = internalDeadline,
            externalDeadline = externalDeadline,
            budget = budget,
            currency = CurrencyCode(currency).let { currencyRepository.get(it) ?: UnknownCurrency(it) },
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
            serviceTypes = serviceTypes.map { it.toDatabaseModel() }.toMutableList(),
            amount = amount,
            expectedStart = expectedStart,
            internalDeadline = internalDeadline,
            externalDeadline = externalDeadline,
            budget = budget,
            currency = currency.id.value,
            status = status.toDatabaseModel(),
            teamMembers = teamMembers.flatMap { it.toDatabaseModel() }.toMutableList(),
            client = client.toDatabaseModel()
        )

        fun ProjectStatusDatabaseModel.toDomain() = when (this) {
            ProjectStatusDatabaseModel.DRAFT -> ProjectStatus.DRAFT
            ProjectStatusDatabaseModel.READY_TO_START -> ProjectStatus.READY_TO_START
            ProjectStatusDatabaseModel.ACTIVE -> ProjectStatus.ACTIVE
            ProjectStatusDatabaseModel.ON_HOLD -> ProjectStatus.ON_HOLD
            ProjectStatusDatabaseModel.REVIEW -> ProjectStatus.REVIEW
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
            ProjectStatus.REVIEW -> ProjectStatusDatabaseModel.REVIEW
            ProjectStatus.READY_TO_DELIVER -> ProjectStatusDatabaseModel.READY_TO_DELIVER
            ProjectStatus.DELIVERED -> ProjectStatusDatabaseModel.DELIVERED
            ProjectStatus.CANCELLED -> ProjectStatusDatabaseModel.CANCELLED
            ProjectStatus.INVOICED -> ProjectStatusDatabaseModel.INVOICED
            ProjectStatus.PAID -> ProjectStatusDatabaseModel.PAID
        }
    }
}
