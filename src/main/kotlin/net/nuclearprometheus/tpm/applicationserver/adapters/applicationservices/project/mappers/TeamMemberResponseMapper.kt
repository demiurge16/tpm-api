package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectShortView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectStatus
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.TeamMemberProjectRole
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.TeamMember
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.TeamMember as ProjectTeamMember

object TeamMemberResponseMapper {

    fun TeamMember.toView(project: Project) = ProjectTeamMember(
        userId = user.id.value,
        firstName = user.firstName,
        lastName = user.lastName,
        email = user.email,
        roles = roles.map {
            TeamMemberProjectRole(
                projectRoleId = it.id.value,
                role = it.role,
                title = it.role.title,
                description = it.role.description
            )
        },
        project = ProjectShortView(
            id = project.id.value,
            title = project.title,
            status = ProjectStatus(
                status = project.status,
                title = project.status.title,
                description = project.status.description
            )
        )
    )
}