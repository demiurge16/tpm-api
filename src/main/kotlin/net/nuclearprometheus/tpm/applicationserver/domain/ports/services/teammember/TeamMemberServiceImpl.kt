package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.teammember

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMember
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberRole
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId

class TeamMemberServiceImpl : TeamMemberService {
    override fun getAll(): List<TeamMember> {
        TODO("Not yet implemented")
    }

    override fun get(ids: List<TeamMemberId>): List<TeamMember> {
        TODO("Not yet implemented")
    }

    override fun get(id: TeamMemberId): TeamMember? {
        TODO("Not yet implemented")
    }

    override fun create(userId: UserId, role: TeamMemberRole, projectId: ProjectId): TeamMember {
        TODO("Not yet implemented")
    }

    override fun delete(id: TeamMemberId) {
        TODO("Not yet implemented")
    }

    override fun changeRole(id: TeamMemberId, role: TeamMemberRole) {
        TODO("Not yet implemented")
    }
}