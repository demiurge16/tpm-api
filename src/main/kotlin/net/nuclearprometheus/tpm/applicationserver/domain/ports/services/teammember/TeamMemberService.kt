package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.teammember

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMember
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberRole
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId

interface TeamMemberService {
    fun getAll(): List<TeamMember>
    fun get(ids: List<TeamMemberId>): List<TeamMember>
    fun get(id: TeamMemberId): TeamMember?
    fun create(userId: UserId, role: TeamMemberRole, projectId: ProjectId): TeamMember
    fun delete(id: TeamMemberId)
    fun changeRole(id: TeamMemberId, role: TeamMemberRole)
}