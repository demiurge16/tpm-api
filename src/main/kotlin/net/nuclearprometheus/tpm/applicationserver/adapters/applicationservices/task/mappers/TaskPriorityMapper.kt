package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.PriorityMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TaskPriorityResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task

object TaskPriorityMapper {

    fun Task.toPriorityChangedView() = TaskPriorityResponse.PriorityChanged(
        taskId = id.value,
        priority = priority.toView()
    )
}