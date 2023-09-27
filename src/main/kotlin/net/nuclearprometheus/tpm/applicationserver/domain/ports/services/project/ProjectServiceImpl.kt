package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.project.ProjectAccessException
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientId
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.TeamMember
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectRole
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.TeamMemberRole
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Thread
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserRole
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.logging.Logger
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import java.math.BigDecimal
import java.time.ZonedDateTime

class ProjectServiceImpl(
    private val projectRepository: ProjectRepository,
    private val languageRepository: LanguageRepository,
    private val accuracyRepository: AccuracyRepository,
    private val industryRepository: IndustryRepository,
    private val unitRepository: UnitRepository,
    private val serviceTypeRepository: ServiceTypeRepository,
    private val currencyRepository: CurrencyRepository,
    private val clientRepository: ClientRepository,
    private val userContextProvider: UserContextProvider,
    private val logger: Logger
) : ProjectService {

    override fun create(
        title: String,
        description: String,
        sourceLanguage: LanguageCode,
        targetLanguages: List<LanguageCode>,
        accuracyId: AccuracyId,
        industryId: IndustryId,
        unitId: UnitId,
        serviceTypeIds: List<ServiceTypeId>,
        amount: Int,
        expectedStart: ZonedDateTime,
        internalDeadline: ZonedDateTime,
        externalDeadline: ZonedDateTime,
        budget: BigDecimal,
        currencyCode: CurrencyCode,
        clientId: ClientId
    ): Project {
        val projectId = ProjectId()
        val createdByUser = userContextProvider.getCurrentUser()

        val project = Project(
            id = projectId,
            title = title,
            description = description,
            sourceLanguage = languageRepository.get(sourceLanguage)
                ?: throw NotFoundException("Source language not found"),
            targetLanguages = languageRepository.get(targetLanguages),
            accuracy = accuracyRepository.get(accuracyId) ?: throw NotFoundException("Accuracy not found"),
            industry = industryRepository.get(industryId) ?: throw NotFoundException("Industry not found"),
            unit = unitRepository.get(unitId) ?: throw NotFoundException("Unit not found"),
            serviceTypes = serviceTypeIds.map { serviceTypeId ->
                serviceTypeRepository.get(serviceTypeId) ?: throw NotFoundException("Service type not found")
            },
            amount = amount,
            expectedStart = expectedStart,
            internalDeadline = internalDeadline,
            externalDeadline = externalDeadline,
            budget = budget,
            currency = currencyRepository.get(currencyCode) ?: throw NotFoundException("Currency not found"),
            client = clientRepository.get(clientId) ?: throw NotFoundException("Client not found"),
            teamMembers = listOf(
                TeamMember(
                    user = createdByUser,
                    roles = listOf(
                        TeamMemberRole(
                            role = ProjectRole.PROJECT_MANAGER,
                            userId = createdByUser.id,
                            projectId = projectId
                        )
                    ),
                    projectId = projectId
                )
            ),
            threads = listOf(
                Thread(
                    title = "General",
                    content = "<p><i>Starting project thread</i></p>",
                    author = createdByUser,
                    projectId = projectId,
                )
            )
        )

        return projectRepository.create(project)
    }

    override fun update(
        id: ProjectId,
        title: String,
        description: String,
        sourceLanguage: LanguageCode,
        targetLanguages: List<LanguageCode>,
        accuracyId: AccuracyId,
        industryId: IndustryId,
        unitId: UnitId,
        serviceTypeIds: List<ServiceTypeId>,
        amount: Int,
        budget: BigDecimal,
        currencyCode: CurrencyCode,
        clientId: ClientId
    ): Project {
        val project = projectRepository.get(id) ?: throw NotFoundException("Project not found")

        project.update(
            title = title,
            description = description,
            sourceLanguage = languageRepository.get(sourceLanguage)
                ?: throw NotFoundException("Source language not found"),
            targetLanguages = languageRepository.get(targetLanguages),
            accuracy = accuracyRepository.get(accuracyId) ?: throw NotFoundException("Accuracy not found"),
            industry = industryRepository.get(industryId) ?: throw NotFoundException("Industry not found"),
            unit = unitRepository.get(unitId) ?: throw NotFoundException("Unit not found"),
            serviceTypes = serviceTypeIds.map { serviceTypeId ->
                serviceTypeRepository.get(serviceTypeId) ?: throw NotFoundException("Service type not found")
            },
            amount = amount,
            budget = budget,
            currency = currencyRepository.get(currencyCode) ?: throw NotFoundException("Currency not found"),
            client = clientRepository.get(clientId) ?: throw NotFoundException("Client not found")
        )

        return projectRepository.update(project)
    }

    override fun moveStart(id: ProjectId, expectedStart: ZonedDateTime): Project {
        val currentUser = userContextProvider.getCurrentUser()
        val project = projectRepository.get(id) ?: throw NotFoundException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) && !project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER)) {
            throw ProjectAccessException("User is not allowed to move project start")
        }

        project.moveStart(expectedStart)
        return projectRepository.update(project)
    }

    override fun moveDeadlines(id: ProjectId, internalDeadline: ZonedDateTime, externalDeadline: ZonedDateTime): Project {
        val currentUser = userContextProvider.getCurrentUser()
        val project = projectRepository.get(id) ?: throw NotFoundException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) && !project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER)) {
            throw ProjectAccessException("User is not allowed to move project deadlines")
        }

        project.moveDeadlines(internalDeadline, externalDeadline)
        return projectRepository.update(project)
    }

    override fun finishDraft(id: ProjectId): Project {
        val currentUser = userContextProvider.getCurrentUser()
        val project = projectRepository.get(id) ?: throw NotFoundException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) && !project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER)) {
            throw ProjectAccessException("User is not allowed to change project status")
        }

        project.finishDraft()
        return projectRepository.update(project)
    }

    override fun backToDraft(id: ProjectId): Project {
        val currentUser = userContextProvider.getCurrentUser()
        val project = projectRepository.get(id) ?: throw NotFoundException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) && !project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER)) {
            throw ProjectAccessException("User is not allowed to change project status")
        }

        project.backToDraft()
        return projectRepository.update(project)
    }

    override fun startProgress(id: ProjectId): Project {
        val currentUser = userContextProvider.getCurrentUser()
        val project = projectRepository.get(id) ?: throw NotFoundException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) && !project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER)) {
            throw ProjectAccessException("User is not allowed to change project status")
        }

        project.startProgress()
        return projectRepository.update(project)
    }

    override fun startReview(id: ProjectId): Project {
        val currentUser = userContextProvider.getCurrentUser()
        val project = projectRepository.get(id) ?: throw NotFoundException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) && !project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER)) {
            throw ProjectAccessException("User is not allowed to change project status")
        }

        project.startReview()
        return projectRepository.update(project)
    }

    override fun approve(id: ProjectId): Project {
        val currentUser = userContextProvider.getCurrentUser()
        val project = projectRepository.get(id) ?: throw NotFoundException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) && !project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER)) {
            throw ProjectAccessException("User is not allowed to change project status")
        }

        project.approve()
        return projectRepository.update(project)
    }

    override fun reject(id: ProjectId): Project {
        val currentUser = userContextProvider.getCurrentUser()
        val project = projectRepository.get(id) ?: throw NotFoundException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) && !project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER)) {
            throw ProjectAccessException("User is not allowed to change project status")
        }

        project.reject()
        return projectRepository.update(project)
    }

    override fun deliver(id: ProjectId): Project {
        val currentUser = userContextProvider.getCurrentUser()
        val project = projectRepository.get(id) ?: throw NotFoundException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) && !project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER)) {
            throw ProjectAccessException("User is not allowed to change project status")
        }

        project.deliver()
        return projectRepository.update(project)
    }

    override fun invoice(id: ProjectId): Project {
        val currentUser = userContextProvider.getCurrentUser()
        val project = projectRepository.get(id) ?: throw NotFoundException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) && !project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER)) {
            throw ProjectAccessException("User is not allowed to change project status")
        }

        project.invoice()
        return projectRepository.update(project)
    }

    override fun pay(id: ProjectId): Project {
        val currentUser = userContextProvider.getCurrentUser()
        val project = projectRepository.get(id) ?: throw NotFoundException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) && !project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER)) {
            throw ProjectAccessException("User is not allowed to change project status")
        }

        project.pay()
        return projectRepository.update(project)
    }

    override fun putOnHold(id: ProjectId): Project {
        val currentUser = userContextProvider.getCurrentUser()
        val project = projectRepository.get(id) ?: throw NotFoundException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) && !project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER)) {
            throw ProjectAccessException("User is not allowed to change project status")
        }

        project.putOnHold()
        return projectRepository.update(project)
    }

    override fun resume(id: ProjectId): Project {
        val currentUser = userContextProvider.getCurrentUser()
        val project = projectRepository.get(id) ?: throw NotFoundException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) && !project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER)) {
            throw ProjectAccessException("User is not allowed to change project status")
        }

        project.resume()
        return projectRepository.update(project)
    }

    override fun cancel(id: ProjectId): Project {
        val currentUser = userContextProvider.getCurrentUser()
        val project = projectRepository.get(id) ?: throw NotFoundException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) && !project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER)) {
            throw ProjectAccessException("User is not allowed to change project status")
        }

        project.cancel()
        return projectRepository.update(project)
    }

    override fun reopen(id: ProjectId): Project {
        val currentUser = userContextProvider.getCurrentUser()
        val project = projectRepository.get(id) ?: throw NotFoundException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) && !project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER)) {
            throw ProjectAccessException("User is not allowed to change project status")
        }

        project.reopen()
        return projectRepository.update(project)
    }
}
