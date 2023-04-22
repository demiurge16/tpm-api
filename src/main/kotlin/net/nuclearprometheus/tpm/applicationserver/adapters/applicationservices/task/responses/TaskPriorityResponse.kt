package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses

import java.util.*

sealed class TaskPriorityResponse {

    data class PriorityChanged(val taskId: UUID, val priority: Priority) : TaskPriorityResponse()
}