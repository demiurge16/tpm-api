package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.task

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.TaskStatusApplicationService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/v1/task/{taskId}")
class TaskStatusController(private val service: TaskStatusApplicationService) {

    private val logger = loggerFor(TaskStatusController::class.java)

    @PatchMapping("/{taskId}/start")
    fun start(@PathVariable(name = "taskId") taskId: UUID) = with(logger) {
        info("PATCH /api/v1/task/$taskId/start")

        service.start(taskId)
    }

    @PatchMapping("/{taskId}/complete")
    fun complete(@PathVariable(name = "taskId") taskId: UUID) = with(logger) {
        info("PATCH /api/v1/task/$taskId/complete")

        service.complete(taskId)
    }

    @PatchMapping("/{taskId}/request-revisions")
    fun requestRevisions(@PathVariable(name = "taskId") taskId: UUID) = with(logger) {
        info("PATCH /api/v1/task/$taskId/request-revisions")

        service.requestRevisions(taskId)
    }

    @PatchMapping("/{taskId}/complete-revisions")
    fun completeRevisions(@PathVariable(name = "taskId") taskId: UUID) = with(logger) {
        info("PATCH /api/v1/task/$taskId/complete-revisions")

        service.completeRevisions(taskId)
    }

    @PatchMapping("/{taskId}/deliver")
    fun deliver(@PathVariable(name = "taskId") taskId: UUID) = with(logger) {
        info("PATCH /api/v1/task/$taskId/deliver")

        service.deliver(taskId)
    }

    @PatchMapping("/{taskId}/cancel")
    fun cancel(@PathVariable(name = "taskId") taskId: UUID) = with(logger) {
        info("PATCH /api/v1/task/$taskId/cancel")

        service.cancel(taskId)
    }

    @PatchMapping("/{taskId}/reopen")
    fun reopen(@PathVariable(name = "taskId") taskId: UUID) = with(logger) {
        info("PATCH /api/v1/task/$taskId/reopen")

        service.reopen(taskId)
    }
}

