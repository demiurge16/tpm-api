package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers

object TimeEntryRefdataMapper {

    fun net.nuclearprometheus.tpm.applicationserver.domain.model.task.TimeEntryStatus.toView() = net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TimeEntryStatus(this, title, description)
    fun net.nuclearprometheus.tpm.applicationserver.domain.model.task.TimeUnit.toView() = net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TimeUnit(this, title, description)
}