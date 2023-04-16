package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses

import java.util.*

sealed class TaskAssignmentResponse {
    data class Assigned(val taskId: UUID, val newAssigneeId: UUID) : TaskAssignmentResponse()
}