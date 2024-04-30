package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers.ProjectMapper.toDeadlineMovedResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers.ProjectMapper.toStartMovedResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers.ProjectMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.Project as ProjectResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.CreateProject
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.MoveProjectDeadline
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.MoveProjectStart
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.UpdateProject
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectDeadlineMoved
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectStartMoved
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientId
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project.ProjectService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserRole
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(propagation = Propagation.REQUIRED)
class ProjectApplicationService(
    private val service: ProjectService,
    private val repository: ProjectRepository,
    private val userContextProvider: UserContextProvider,
    private val specificationBuilder: SpecificationBuilder<Project>
) {

    private val logger = loggerFor(ProjectApplicationService::class.java)

    fun getProjects(query: FilteredRequest<Project>): Page<ProjectResponse> {
        logger.info("getProjects($query)")
        val user = userContextProvider.getCurrentUser()

        return if (user.roles.contains(UserRole.ADMIN)) {
            repository.get(query.toQuery(specificationBuilder)).map { it.toView() }
        } else {
            repository.getProjectsForUser(user.id, query.toQuery(specificationBuilder)).map { it.toView() }
        }
    }

    fun getProject(id: UUID) = with(logger) {
        info("getProject($id)")

        repository.get(ProjectId(id))?.toView() ?: throw NotFoundException("Project with id $id not found")
    }

    fun createProject(request: CreateProject): ProjectResponse {
        logger.info("createProject($request)")
        return service.create(
            title = request.title,
            description = request.description,
            sourceLanguage = LanguageCode(request.sourceLanguage),
            targetLanguages = request.targetLanguages.map { LanguageCode(it) },
            accuracyId = AccuracyId(request.accuracyId),
            industryId = IndustryId(request.industryId),
            unitId = UnitId(request.unitId),
            serviceTypeIds = request.serviceTypeIds.map { ServiceTypeId(it) },
            amount = request.amount,
            expectedStart = request.expectedStart,
            internalDeadline = request.internalDeadline,
            externalDeadline = request.externalDeadline,
            budget = request.budget,
            currencyCode = CurrencyCode(request.currencyCode),
            clientId = ClientId(request.clientId)
        ).toView()
    }

    fun updateProject(id: UUID, request: UpdateProject): ProjectResponse {
        logger.info("updateProject($id, $request)")
        return service.update(
            id = ProjectId(id),
            title = request.title,
            description = request.description,
            sourceLanguage = LanguageCode(request.sourceLanguage),
            targetLanguages = request.targetLanguages.map { LanguageCode(it) },
            accuracyId = AccuracyId(request.accuracyId),
            industryId = IndustryId(request.industryId),
            unitId = UnitId(request.unitId),
            serviceTypeIds = request.serviceTypeIds.map { ServiceTypeId(it) },
            amount = request.amount,
            budget = request.budget,
            currencyCode = CurrencyCode(request.currencyCode),
            clientId = ClientId(request.clientId)
        ).toView()
    }

    fun moveProjectStart(id: UUID, request: MoveProjectStart): ProjectStartMoved {
        logger.info("moveProjectStart($id, $request)")
        return service.moveStart(ProjectId(id), request.expectedStart).toStartMovedResponse()
    }

    fun moveProjectDeadline(id: UUID, request: MoveProjectDeadline): ProjectDeadlineMoved {
        logger.info("moveProjectDeadline($id, $request)")
        return service.moveDeadlines(ProjectId(id), request.internalDeadline, request.externalDeadline)
            .toDeadlineMovedResponse()
    }
}