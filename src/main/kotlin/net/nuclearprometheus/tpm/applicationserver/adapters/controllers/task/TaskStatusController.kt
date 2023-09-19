package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.task

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.TaskStatusApplicationService
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/v1/task/{taskId}")
class TaskStatusController(private val service: TaskStatusApplicationService) {

    private val logger = loggerFor(TaskStatusController::class.java)

    @PatchMapping("/start")
    fun start(@PathVariable(name = "taskId") taskId: UUID) = with(logger) {
        info("PATCH /api/v1/task/$taskId/start")

        ResponseEntity.ok().body(service.start(taskId))
    }

    @PatchMapping("/start-review")
    fun complete(@PathVariable(name = "taskId") taskId: UUID) = with(logger) {
        info("PATCH /api/v1/task/$taskId/start-review")

        ResponseEntity.ok().body(service.startReview(taskId))
    }

    @PatchMapping("/reject")
    fun requestRevisions(@PathVariable(name = "taskId") taskId: UUID) = with(logger) {
        info("PATCH /api/v1/task/$taskId/reject")

        ResponseEntity.ok().body(service.reject(taskId))
    }

    @PatchMapping("/approve")
    fun completeRevisions(@PathVariable(name = "taskId") taskId: UUID) = with(logger) {
        info("PATCH /api/v1/task/$taskId/approve")

        ResponseEntity.ok().body(service.approve(taskId))
    }

    @PatchMapping("/put-on-hold")
    fun putOnHold(@PathVariable(name = "taskId") taskId: UUID) = with(logger) {
        info("PATCH /api/v1/task/$taskId/put-on-hold")

        ResponseEntity.ok().body(service.putOnHold(taskId))
    }

    @PatchMapping("/resume")
    fun resume(@PathVariable(name = "taskId") taskId: UUID) = with(logger) {
        info("PATCH /api/v1/task/$taskId/resume")

        ResponseEntity.ok().body(service.resume(taskId))
    }

    @PatchMapping("/complete")
    fun deliver(@PathVariable(name = "taskId") taskId: UUID) = with(logger) {
        info("PATCH /api/v1/task/$taskId/complete")

        ResponseEntity.ok().body(service.complete(taskId))
    }

    @PatchMapping("/cancel")
    fun cancel(@PathVariable(name = "taskId") taskId: UUID) = with(logger) {
        info("PATCH /api/v1/task/$taskId/cancel")

        ResponseEntity.ok().body(service.cancel(taskId))
    }

    @PatchMapping("/reopen")
    fun reopen(@PathVariable(name = "taskId") taskId: UUID) = with(logger) {
        info("PATCH /api/v1/task/$taskId/reopen")

        ResponseEntity.ok().body(service.reopen(taskId))
    }
}
