package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectTeamMemberResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMember

object ProjectTeamMemberResponseMapper {

    fun TeamMember.toView() = ProjectTeamMemberResponse.View(
        id = id.value,
        userId = user.id.value,
        firstName = user.firstName,
        lastName = user.lastName,
        email = user.email,
        role = ProjectTeamMemberResponse.View.TeamMemberRoleView(
            role = role,
            title = role.title,
            description = role.description
        ),
        projectId = projectId.value
    )
}