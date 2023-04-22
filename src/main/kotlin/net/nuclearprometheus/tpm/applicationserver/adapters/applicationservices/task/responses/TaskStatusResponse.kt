package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses

import java.util.*

sealed class TaskStatusResponse {

    data class NewStatus(val taskId: UUID, val status: Status) : TaskStatusResponse()
}