package net.nuclearprometheus.tpm.applicationserver.domain.model.teammember

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId

class TeamMember(
    user: User,
    roles: List<TeamMemberRole>,
    projectId: ProjectId
) : Entity<TeamMemberId>(TeamMemberId(user.id.value)) {

    var user = user; private set
    var roles = roles; private set
    var projectId = projectId; private set
}
