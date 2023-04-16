package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.task

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.TaskAssignmentApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests.TaskAssignmentRequest
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/task/{taskId}")
class TaskAssignmentController(private val taskAssignmentApplicationService: TaskAssignmentApplicationService) {

    private val logger = loggerFor(TaskAssignmentController::class.java)

    @PatchMapping("/assign-team-member")
    fun assignTeamMember(
        @PathVariable(name = "taskId") taskId: UUID,
        @RequestBody request: TaskAssignmentRequest.Assign
    ) = with(logger) {
        info("PATCH /api/v1/task/$taskId/assign-team-member")

        taskAssignmentApplicationService.assignTeamMember(taskId, request)
    }

    @PatchMapping("/unassign-team-member")
    fun unassignTeamMember(@PathVariable(name = "taskId") taskId: UUID) = with(logger) {
        info("PATCH /api/v1/task/$taskId/unassign-team-member")

        taskAssignmentApplicationService.unassignTeamMember(taskId)
    }
}

