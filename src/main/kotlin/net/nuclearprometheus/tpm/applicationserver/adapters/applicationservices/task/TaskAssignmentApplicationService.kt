package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers.TaskAssignmentMapper.toAssignee
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests.TaskAssignmentRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.task.TaskService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.util.*

@Service
class TaskAssignmentApplicationService(private val taskService: TaskService) {

    private val logger = loggerFor(TaskAssignmentApplicationService::class.java)

    fun assignTeamMember(taskId: UUID, request: TaskAssignmentRequest.Assign) = with(logger) {
        info("assignTeamMember($taskId, $request)")

        taskService.assignTeamMember(TaskId(taskId), TeamMemberId(request.teamMemberId)).toAssignee()
    }

    fun unassignTeamMember(taskId: UUID) {
        with(logger) {
            info("unassignTeamMember($taskId)")

            taskService.unassignTeamMember(TaskId(taskId)).toAssignee()
        }
    }
}