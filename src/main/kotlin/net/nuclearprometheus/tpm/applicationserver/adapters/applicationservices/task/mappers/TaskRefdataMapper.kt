package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TaskRefdataResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskStatus

object TaskRefdataMapper {

    fun TaskStatus.toView() = TaskRefdataResponse.StatusView(this, name, description)
}