package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.Status
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskStatus

object TaskRefdataMapper {

    fun TaskStatus.toView() = Status(this, name, description)
}