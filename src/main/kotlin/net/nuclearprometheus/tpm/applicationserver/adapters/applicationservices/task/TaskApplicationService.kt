package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers.TaskMapper.toDeadlineMoved
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers.TaskMapper.toStartMoved
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers.TaskMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.Task as TaskResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests.TaskMoveDeadline
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests.TaskMoveStart
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests.UpdateTask
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TaskDeadlineMoved
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TaskStartMoved
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.task.TaskRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.task.TaskService
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(propagation = Propagation.REQUIRED)
class TaskApplicationService(
    private val service: TaskService,
    private val repository: TaskRepository,
    private val projectRepository: ProjectRepository,
    private val specificationBuilder: SpecificationBuilder<Task>
) {

    private val logger = loggerFor(TaskApplicationService::class.java)

    fun getTasks(query: FilteredRequest<Task>): Page<TaskResponse> {
        logger.info("getTasks($query)")
        return repository.get(query.toQuery(specificationBuilder)).map {
            val project = projectRepository.get(it.projectId) ?: throw IllegalStateException("Project with id ${it.projectId} not found")
            it.toView(project)
        }
    }

    fun getTask(taskId: UUID): TaskResponse {
        logger.info("getTask($taskId)")

        val task = repository.get(TaskId(taskId)) ?: throw NotFoundException("Task with id $taskId not found")
        val project = projectRepository.get(task.projectId) ?: throw IllegalStateException("Project with id ${task.projectId} not found")

        return task.toView(project)
    }

    fun updateTask(taskId: UUID, request: UpdateTask): TaskResponse {
        logger.info("updateTask($taskId, $request)")

        val task = service.update(
            id = TaskId(taskId),
            title = request.title,
            description = request.description,
            sourceLanguage = LanguageCode(request.sourceLanguage),
            targetLanguage = LanguageCode(request.targetLanguage),
            accuracy = AccuracyId(request.accuracy),
            industry = IndustryId(request.industry),
            unit = UnitId(request.unit),
            serviceTypeId = ServiceTypeId(request.serviceType),
            amount = request.amount,
            budget = request.budget,
            currency = CurrencyCode(request.currency),
        )
        val project = projectRepository.get(task.projectId) ?: throw IllegalStateException("Project with id ${task.projectId} not found")
        return task.toView(project)
    }

    fun moveStart(taskId: UUID, request: TaskMoveStart): TaskStartMoved {
        logger.info("moveStart($taskId)")
        return service.moveStart(TaskId(taskId), request.start).toStartMoved()
    }

    fun moveDeadline(taskId: UUID, request: TaskMoveDeadline): TaskDeadlineMoved {
        logger.info("moveDeadline($taskId)")
        return service.moveDeadline(TaskId(taskId), request.deadline).toDeadlineMoved()
    }
}