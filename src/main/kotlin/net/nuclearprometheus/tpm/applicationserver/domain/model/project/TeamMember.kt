package net.nuclearprometheus.tpm.applicationserver.domain.model.project

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User

class TeamMember(
    user: User,
    roles: List<TeamMemberRole>,
    projectId: ProjectId
) : Entity<TeamMemberId>(TeamMemberId(user.id.value)) {

    var user = user; private set
    var roles = roles; private set
    var projectId = projectId; private set

    fun hasRole(role: ProjectRole) = roles.any { it.role == role }

    fun hasAnyRole(roles: List<ProjectRole>) = roles.any { hasRole(it) }
}
