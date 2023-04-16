package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses

import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskStatus
import java.util.*

sealed class TaskStatusResponse {

    data class NewStatus(val taskId: UUID, val status: TaskStatusView) : TaskStatusResponse() {
        data class TaskStatusView(
            val status: TaskStatus,
            val name: String,
            val description: String,
        )
    }
}