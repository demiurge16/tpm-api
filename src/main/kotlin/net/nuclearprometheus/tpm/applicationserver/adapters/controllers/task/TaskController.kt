package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.task

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.TaskApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests.TaskRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/task")
class TaskController(private val service: TaskApplicationService) {

    private val logger = loggerFor(TaskController::class.java)

    @GetMapping("")
    fun getTasks() = with(logger) {
        info("GET /api/v1/task")

        service.getTasks()
    }

    @GetMapping("/{taskId}")
    fun getTask(@PathVariable(name = "taskId") taskId: UUID) = with(logger) {
        info("GET /api/v1/task/$taskId")

        service.getTask(taskId)
    }

    @PutMapping("/{taskId}")
    fun updateTask(@PathVariable(name = "taskId") taskId: UUID, @RequestBody request: TaskRequest.Update) =
        with(logger) {
            info("PUT /api/v1/task/$taskId")

            service.updateTask(taskId, request)
        }

    @PatchMapping("/{taskId}/move-start")
    fun moveStart(@PathVariable(name = "taskId") taskId: UUID, @RequestBody request: TaskRequest.MoveStart) =
        with(logger) {
            info("PATCH /api/v1/task/$taskId/move-start")

            service.moveStart(taskId, request)
        }

    @PatchMapping("/{taskId}/move-deadline")
    fun moveDeadline(@PathVariable(name = "taskId") taskId: UUID, @RequestBody request: TaskRequest.MoveDeadline) =
        with(logger) {
            info("PATCH /api/v1/task/$taskId/move-deadline")

            service.moveDeadline(taskId, request)
        }
}

