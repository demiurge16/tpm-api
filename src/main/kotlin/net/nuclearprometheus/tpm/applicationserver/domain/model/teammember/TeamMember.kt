package net.nuclearprometheus.tpm.applicationserver.domain.model.teammember

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User

class TeamMember(
    val id: TeamMemberId = TeamMemberId(),
    user: User,
    role: TeamMemberRole,
    projectId: ProjectId
) {
    var user = user; private set
    var role = role; private set
    var projectId = projectId; private set

    fun changeRole(role: TeamMemberRole) {
        this.role = role
    }
}
