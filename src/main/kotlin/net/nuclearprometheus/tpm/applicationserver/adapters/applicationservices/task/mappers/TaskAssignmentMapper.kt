package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.Assignee
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TaskAssignmentResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task

object TaskAssignmentMapper {
    fun Task.toAssignee() = TaskAssignmentResponse.Assigned(
        taskId = id.value,
        newAssignee = assignee?.let {
            Assignee(
                userId = it.id.value,
                firstName = it.firstName,
                lastName = it.lastName,
                email = it.email
            )
        } ?: throw IllegalStateException("Assignment to task $id was not successful.")
    )
}