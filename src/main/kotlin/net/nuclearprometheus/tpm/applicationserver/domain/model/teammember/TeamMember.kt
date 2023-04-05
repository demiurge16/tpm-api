package net.nuclearprometheus.tpm.applicationserver.domain.model.teammember

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User

class TeamMember(
    id: TeamMemberId = TeamMemberId(),
    user: User,
    role: TeamMemberRole,
    projectId: ProjectId
) : Entity<TeamMemberId>(id) {
    var user = user; private set
    var role = role; private set
    var projectId = projectId; private set

    fun changeRole(role: TeamMemberRole) {
        this.role = role
    }
}
