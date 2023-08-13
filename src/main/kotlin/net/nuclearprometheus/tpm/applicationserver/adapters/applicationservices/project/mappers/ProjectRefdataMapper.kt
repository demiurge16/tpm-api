package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.Role
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.Status
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectStatus
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberRole

object ProjectRefdataMapper {

    fun ProjectStatus.toView() = Status(this, title, description)
    fun TeamMemberRole.toView() = Role(this, title, description)
}