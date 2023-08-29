package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers.TaskMapper.toDeadlineMoved
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers.TaskMapper.toStartMoved
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers.TaskMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests.TaskRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.task.TaskRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.task.TaskService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.util.*

@Service
class TaskApplicationService(private val service: TaskService, private val repository: TaskRepository) {

    private val logger = loggerFor(TaskApplicationService::class.java)

    fun getTasks(query: FilteredRequest<Task>) = with(logger) {
        info("getTasks($query)")
        repository.get(query.toQuery()).map { it.toView() }
    }

    fun getTask(taskId: UUID) = with(logger) {
        info("getTask($taskId)")

        repository.get(TaskId(taskId))?.toView() ?: throw NotFoundException("Task with id $taskId not found")
    }

    fun updateTask(taskId: UUID, request: TaskRequest.Update) = with(logger) {
        info("updateTask($taskId, $request)")

        service.update(
            id = TaskId(taskId),
            title = request.title,
            description = request.description,
            sourceLanguage = LanguageCode(request.sourceLanguage),
            targetLanguage = LanguageCode(request.targetLanguage),
            accuracy = AccuracyId(request.accuracy),
            industry = IndustryId(request.industry),
            unit = UnitId(request.unit),
            amount = request.amount,
            budget = request.budget,
            currency = CurrencyCode(request.currency),
        ).toView()
    }

    fun moveStart(taskId: UUID, request: TaskRequest.MoveStart) = with(logger) {
        info("moveStart($taskId)")

        service.moveStart(TaskId(taskId), request.start).toStartMoved()
    }

    fun moveDeadline(taskId: UUID, request: TaskRequest.MoveDeadline) = with(logger) {
        info("moveDeadline($taskId)")

        service.moveDeadline(TaskId(taskId), request.deadline).toDeadlineMoved()
    }
}