package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TaskPriorityResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task

object TaskPriorityMapper {

    fun Task.toPriorityChangedView() = TaskPriorityResponse.PriorityChanged(
        taskId = id.value,
        priority = TaskPriorityResponse.PriorityChanged.PriorityView(
            id = priority.id.value,
            name = priority.name,
            description = priority.description,
            emoji = priority.emoji,
            value = priority.value
        )
    )
}