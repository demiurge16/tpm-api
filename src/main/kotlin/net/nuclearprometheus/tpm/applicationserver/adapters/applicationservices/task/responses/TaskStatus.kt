package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses

import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskStatus

data class TaskStatus(
    val status: TaskStatus,
    val title: String,
    val description: String,
)