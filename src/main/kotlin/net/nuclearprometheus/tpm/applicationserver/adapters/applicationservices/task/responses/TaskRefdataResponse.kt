package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses

import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskStatus

sealed class TaskRefdataResponse {

    data class StatusView(
        val status: TaskStatus,
        val name: String,
        val description: String,
    )
}