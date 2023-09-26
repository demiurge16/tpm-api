package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.TeamMember
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository

interface TeamMemberRepository : BaseRepository<TeamMember, TeamMemberId> {
    fun getAllByProjectId(projectId: ProjectId): List<TeamMember>
    fun deleteAllByProjectId(projectId: ProjectId)
    fun getByUserIdAndProjectId(userId: UserId, projectId: ProjectId): TeamMember?
}
