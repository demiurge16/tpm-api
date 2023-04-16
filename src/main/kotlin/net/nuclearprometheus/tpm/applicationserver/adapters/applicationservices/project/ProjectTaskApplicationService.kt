package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project

import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.singlePage
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers.ProjectTaskMapper.toAssignee
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectTaskRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.task.TaskRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.task.TaskService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProjectTaskApplicationService(
    private val taskService: TaskService,
    private val taskRepository: TaskRepository
) {

    private val logger = loggerFor(ProjectTaskApplicationService::class.java)

    fun getTasksForProject(projectId: UUID) = with(logger) {
        info("getTasksForProject($projectId)")

        singlePage(taskRepository.getAllByProjectId(ProjectId(projectId))).map { it.toAssignee() }
    }

    fun createTask(projectId: UUID, request: ProjectTaskRequest.Create) = with(logger) {
        info("createTask($projectId, $request)")

        taskService.create(
            title = request.title,
            description = request.description,
            sourceLanguage = LanguageCode(request.sourceLanguage),
            targetLanguage = LanguageCode(request.targetLanguage),
            accuracy = AccuracyId(request.accuracy),
            industry = IndustryId(request.industry),
            unit = UnitId(request.unit),
            amount = request.amount,
            expectedStart = request.expectedStart,
            deadline = request.deadline,
            budget = request.budget,
            currency = CurrencyCode(request.currency),
            priorityId = PriorityId(request.priorityId),
            projectId = ProjectId(request.projectId)
        ).toAssignee()
    }
}