package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.teammember

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMember
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.ProjectRole
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberRoleId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId

interface TeamMemberService {
    fun giveRoleToUser(userId: UserId, role: ProjectRole, projectId: ProjectId): TeamMember
    fun removeRoleFromUser(id: TeamMemberRoleId)
}
