package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectRefdataResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectStatus

object ProjectRefdataMapper {

    fun ProjectStatus.toView() = ProjectRefdataResponse.StatusView(this, name, shortDescription)
}