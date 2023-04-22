package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.Status
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectStatus

object ProjectRefdataMapper {

    fun ProjectStatus.toView() = Status(this, name, shortDescription)
}