package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.Priority
import java.util.*

sealed class TaskPriorityResponse {

    data class PriorityChanged(val taskId: UUID, val priority: Priority) : TaskPriorityResponse()
}