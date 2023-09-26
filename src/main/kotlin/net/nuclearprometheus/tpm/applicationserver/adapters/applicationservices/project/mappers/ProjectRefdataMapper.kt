package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectStatus as ProjectStatusResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectRole as ProjectRoleResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectRole
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectStatus

object ProjectRefdataMapper {

    fun ProjectStatus.toView() = ProjectStatusResponse(this, title, description)
    fun ProjectRole.toView() = ProjectRoleResponse(this, title, description)
}
