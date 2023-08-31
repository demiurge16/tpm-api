package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers.ProjectMapper.toDeadlineMovedResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers.ProjectMapper.toStartMovedResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers.ProjectMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientId
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project.ProjectService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project.security.ProjectPermissionService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProjectApplicationService(
    private val service: ProjectService,
    private val repository: ProjectRepository,
    private val userContextProvider: UserContextProvider,
    private val projectPermissionService: ProjectPermissionService
) {

    private val logger = loggerFor(ProjectApplicationService::class.java)

    fun getProjects(query: FilteredRequest<Project>) = with(logger) {
        info("getProjects($query)")
        repository.get(query.toQuery()).map { it.toView() }
    }

    fun getProject(id: UUID) = with(logger) {
        info("getProject($id)")

        repository.get(ProjectId(id))?.toView() ?: throw NotFoundException("Project with id $id not found")
    }

    fun createProject(request: ProjectRequest.Create) = with(logger) {
        info("createProject($request)")

        val project = service.create(
            title = request.title,
            description = request.description,
            sourceLanguage = LanguageCode(request.sourceLanguage),
            targetLanguages = request.targetLanguages.map { LanguageCode(it) },
            accuracyId = AccuracyId(request.accuracyId),
            industryId = IndustryId(request.industryId),
            unitId = UnitId(request.unitId),
            amount = request.amount,
            expectedStart = request.expectedStart,
            internalDeadline = request.internalDeadline,
            externalDeadline = request.externalDeadline,
            budget = request.budget,
            currencyCode = CurrencyCode(request.currencyCode),
            clientId = ClientId(request.clientId),
            createdById = userContextProvider.getCurrentUser().id
        )

        projectPermissionService.createProjectResource(project.id)
        projectPermissionService.addUserProjectPermission(userContextProvider.getCurrentUser().id, project.id)

        project.toView()
    }

    fun updateProject(id: UUID, request: ProjectRequest.Update) = with(logger) {
        info("updateProject($id, $request)")

        service.update(
            id = ProjectId(id),
            title = request.title,
            description = request.description,
            sourceLanguage = LanguageCode(request.sourceLanguage),
            targetLanguages = request.targetLanguages.map { LanguageCode(it) },
            accuracyId = AccuracyId(request.accuracyId),
            industryId = IndustryId(request.industryId),
            unitId = UnitId(request.unitId),
            amount = request.amount,
            budget = request.budget,
            currencyCode = CurrencyCode(request.currencyCode),
            clientId = ClientId(request.clientId)
        ).toView()
    }

    fun moveProjectStart(id: UUID, request: ProjectRequest.MoveStart) = with(logger) {
        info("moveProjectStart($id, $request)")

        service.moveStart(ProjectId(id), request.expectedStart).toStartMovedResponse()
    }

    fun moveProjectDeadline(id: UUID, request: ProjectRequest.MoveDeadline) = with(logger) {
        info("moveProjectDeadline($id, $request)")

        service.moveDeadlines(ProjectId(id), request.internalDeadline, request.externalDeadline)
            .toDeadlineMovedResponse()
    }
}