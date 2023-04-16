package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TaskAssignmentResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task

object TaskAssignmentMapper {
    fun Task.toAssignee() = TaskAssignmentResponse.Assigned(
        taskId = id.value,
        newAssigneeId = assignee?.let { id.value }
            ?: throw IllegalStateException("Task is not assigned to a team member"),
    )
}