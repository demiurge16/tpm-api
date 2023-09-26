package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.task

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.TaskPriorityApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests.TaskChangePriority
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/task/{taskId}")
class TaskPriorityController(private val service: TaskPriorityApplicationService) {

    private val logger = loggerFor(TaskPriorityController::class.java)

    @PatchMapping("/change-priority")
    fun changePriority(
        @PathVariable(name = "taskId") taskId: UUID,
        @RequestBody request: TaskChangePriority
    ) = with(logger) {
        info("PATCH /api/v1/task/$taskId/change-priority")

        ResponseEntity.ok().body(service.changePriority(taskId, request))
    }
}

