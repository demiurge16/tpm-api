package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses

import java.util.*

sealed class TaskPriorityResponse {

    data class PriorityChanged(val taskId: UUID, val priority: PriorityView) : TaskPriorityResponse() {
        data class PriorityView(
            val id: UUID,
            val name: String,
            val description: String,
            val emoji: String,
            val value: Int
        )
    }
}