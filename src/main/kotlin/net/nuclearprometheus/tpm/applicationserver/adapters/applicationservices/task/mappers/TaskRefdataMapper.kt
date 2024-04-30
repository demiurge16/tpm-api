package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers

object TaskRefdataMapper {

    fun net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskStatus.toView() = net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TaskStatus(this, name, description)
}
