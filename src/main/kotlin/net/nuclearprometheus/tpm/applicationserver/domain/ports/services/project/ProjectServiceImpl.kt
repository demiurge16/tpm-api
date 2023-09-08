package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientId
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMember
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberRole
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Thread
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.logging.Logger
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project.security.ProjectPermissionService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project.security.getGrantedScopes
import java.math.BigDecimal
import java.time.ZonedDateTime

class ProjectServiceImpl(
    private val projectRepository: ProjectRepository,
    private val languageRepository: LanguageRepository,
    private val accuracyRepository: AccuracyRepository,
    private val industryRepository: IndustryRepository,
    private val unitRepository: UnitRepository,
    private val currencyRepository: CurrencyRepository,
    private val clientRepository: ClientRepository,
    private val userRepository: UserRepository,
    private val projectPermissionService: ProjectPermissionService,
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
        amount: Int,
        expectedStart: ZonedDateTime,
        internalDeadline: ZonedDateTime,
        externalDeadline: ZonedDateTime,
        budget: BigDecimal,
        currencyCode: CurrencyCode,
        clientId: ClientId,
        createdById: UserId
    ): Project {
        val projectId = ProjectId()
        val createdByUser = userRepository.get(createdById) ?: throw NotFoundException("User not found")

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
                    role = TeamMemberRole.PROJECT_MANAGER,
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
            .also {
                logger.info("Project created: ${it.id.value}")

                projectPermissionService.createProjectResources(project)
                project.teamMembers.forEach { teamMember ->
                    teamMember.role.getGrantedScopes().forEach { scope ->
                        projectPermissionService.grantUserProjectPermission(teamMember.user, project, scope)
                    }
                }
            }
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
            amount = amount,
            budget = budget,
            currency = currencyRepository.get(currencyCode) ?: throw NotFoundException("Currency not found"),
            client = clientRepository.get(clientId) ?: throw NotFoundException("Client not found")
        )

        return projectRepository.update(project)
    }

    override fun moveStart(id: ProjectId, expectedStart: ZonedDateTime) = projectRepository.get(id)?.let { project ->
        project.moveStart(expectedStart)
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun moveDeadlines(id: ProjectId, internalDeadline: ZonedDateTime, externalDeadline: ZonedDateTime) =
        projectRepository.get(id)?.let { project ->
            project.moveDeadlines(internalDeadline, externalDeadline)
            projectRepository.update(project)
        } ?: throw NotFoundException("Project not found")

    override fun finishDraft(id: ProjectId) = projectRepository.get(id)?.let { project ->
        project.finishDraft()
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun backToDraft(id: ProjectId) = projectRepository.get(id)?.let { project ->
        project.backToDraft()
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun startProgress(id: ProjectId) = projectRepository.get(id)?.let { project ->
        project.startProgress()
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun startReview(id: ProjectId) = projectRepository.get(id)?.let { project ->
        project.startReview()
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun approve(id: ProjectId) = projectRepository.get(id)?.let { project ->
        project.approve()
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun reject(id: ProjectId) = projectRepository.get(id)?.let { project ->
        project.reject()
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun deliver(id: ProjectId) = projectRepository.get(id)?.let { project ->
        project.deliver()
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun invoice(id: ProjectId) = projectRepository.get(id)?.let { project ->
        project.invoice()
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun pay(id: ProjectId) = projectRepository.get(id)?.let { project ->
        project.pay()
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun putOnHold(id: ProjectId) = projectRepository.get(id)?.let { project ->
        project.putOnHold()
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun resume(id: ProjectId) = projectRepository.get(id)?.let { project ->
        project.resume()
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun cancel(id: ProjectId) = projectRepository.get(id)?.let { project ->
        project.cancel()
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun reopen(id: ProjectId) = projectRepository.get(id)?.let { project ->
        project.reopen()
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")
}
