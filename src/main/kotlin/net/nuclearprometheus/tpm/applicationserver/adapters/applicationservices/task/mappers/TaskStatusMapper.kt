package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TaskStatus
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TaskStatusResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task

object TaskStatusMapper {

    fun Task.toTaskStatusResponse() = TaskStatusResponse.NewStatus(
        taskId = id.value,
        status = TaskStatus(
            status = status,
            title = status.title,
            description = status.description
        )
    )
}
