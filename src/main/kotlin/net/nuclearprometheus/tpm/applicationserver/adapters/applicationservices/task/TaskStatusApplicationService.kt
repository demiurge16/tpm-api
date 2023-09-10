package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers.TaskStatusMapper.toTaskStatusResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.task.TaskService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(propagation = Propagation.REQUIRED)
class TaskStatusApplicationService(private val service: TaskService) {

    private val logger = loggerFor(TaskStatusApplicationService::class.java)

    fun start(taskId: UUID) = with(logger) {
        info("start($taskId)")

        service.start(TaskId(taskId)).toTaskStatusResponse()
    }

    fun complete(taskId: UUID) = with(logger) {
        info("complete($taskId)")

        service.complete(TaskId(taskId)).toTaskStatusResponse()
    }

    fun requestRevisions(taskId: UUID) = with(logger) {
        info("requestRevisions($taskId)")

        service.requestRevisions(TaskId(taskId)).toTaskStatusResponse()
    }

    fun completeRevisions(taskId: UUID) = with(logger) {
        info("completeRevisions($taskId)")

        service.completeRevisions(TaskId(taskId)).toTaskStatusResponse()
    }

    fun deliver(taskId: UUID) = with(logger) {
        info("deliver($taskId)")

        service.deliver(TaskId(taskId)).toTaskStatusResponse()
    }

    fun cancel(taskId: UUID) = with(logger) {
        info("cancel($taskId)")

        service.cancel(TaskId(taskId)).toTaskStatusResponse()
    }

    fun reopen(taskId: UUID) = with(logger) {
        info("reopen($taskId)")

        service.reopen(TaskId(taskId)).toTaskStatusResponse()
    }
}
