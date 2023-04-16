package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TaskStatusResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task

object TaskStatusMapper {

    fun Task.toTaskStatusResponse() = TaskStatusResponse.NewStatus(
        taskId = id.value,
        status = TaskStatusResponse.NewStatus.TaskStatusView(
            status = status,
            name = status.name,
            description = status.description
        )
    )
}