package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests

import java.util.*

sealed class TaskPriorityRequest {

    data class ChangePriority(val priorityId: UUID) : TaskPriorityRequest()
}