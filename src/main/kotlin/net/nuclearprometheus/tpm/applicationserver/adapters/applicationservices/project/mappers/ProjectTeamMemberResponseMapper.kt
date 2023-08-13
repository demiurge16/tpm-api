package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectShortView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.Role
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.Status
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.TeamMember as ProjectTeamMember
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMember

object ProjectTeamMemberResponseMapper {

    fun TeamMember.toView(project: Project) = ProjectTeamMember(
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
        project = ProjectShortView(
            id = project.id.value,
            title = project.title,
            status = Status(
                status = project.status,
                title = project.status.title,
                description = project.status.description
            )
        )
    )
}