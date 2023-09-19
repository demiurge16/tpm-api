package net.nuclearprometheus.tpm.applicationserver.domain.model.teammember

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId

class TeamMemberRole(
    id: TeamMemberRoleId = TeamMemberRoleId(),
    role: ProjectRole,
    userId: UserId,
    projectId: ProjectId
) : Entity<TeamMemberRoleId>(id) {
    var role = role; private set
    var userId = userId; private set
    var projectId = projectId; private set
}