package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectTaskRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers.TaskMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TaskResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.task.TaskRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.task.TaskService
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.emptyPage
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(propagation = Propagation.REQUIRED)
class ProjectTaskApplicationService(
    private val taskService: TaskService,
    private val taskRepository: TaskRepository,
    private val projectRepository: ProjectRepository
) {

    private val logger = loggerFor(ProjectTaskApplicationService::class.java)

    fun getTasksForProject(projectId: UUID, query: FilteredRequest<Task>): Page<TaskResponse.Task> {
        logger.info("getTasksForProject($projectId)")

        val project = projectRepository.get(ProjectId(projectId)) ?: return emptyPage()
        return taskRepository.getAllByProjectIdAndQuery(ProjectId(projectId), query.toQuery()).map { it.toView(project) }
    }

    fun createTask(projectId: UUID, request: ProjectTaskRequest.Create): TaskResponse.Task {
        logger.info("createTask($projectId, $request)")

        val project = projectRepository.get(ProjectId(projectId)) ?: throw IllegalArgumentException("Project with id $projectId not found")
        return taskService.create(
            title = request.title,
            description = request.description,
            sourceLanguage = LanguageCode(request.sourceLanguage),
            targetLanguage = LanguageCode(request.targetLanguage),
            accuracy = AccuracyId(request.accuracyId),
            industry = IndustryId(request.industryId),
            unit = UnitId(request.unitId),
            serviceTypeId = ServiceTypeId(request.serviceTypeId),
            amount = request.amount,
            expectedStart = request.expectedStart,
            deadline = request.deadline,
            budget = request.budget,
            currency = CurrencyCode(request.currencyCode),
            priorityId = PriorityId(request.priorityId),
            projectId = ProjectId(projectId)
        ).toView(project)
    }
}