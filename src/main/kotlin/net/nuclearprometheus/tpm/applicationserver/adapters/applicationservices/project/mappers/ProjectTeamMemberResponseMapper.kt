package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.Role
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.TeamMember as ProjectTeamMember
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMember

object ProjectTeamMemberResponseMapper {

    fun TeamMember.toView() = ProjectTeamMember(
        id = id.value,
        userId = user.id.value,
        firstName = user.firstName,
        lastName = user.lastName,
        email = user.email,
        role = Role(
            role = role,
            title = role.title,
            description = role.description
        ),
        projectId = projectId.value
    )
}