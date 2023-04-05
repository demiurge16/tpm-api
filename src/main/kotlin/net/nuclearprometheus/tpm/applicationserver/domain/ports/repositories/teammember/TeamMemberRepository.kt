package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMember
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository

interface TeamMemberRepository : BaseRepository<TeamMember, TeamMemberId> {
    fun getAllByProjectId(projectId: ProjectId): List<TeamMember>
    fun deleteAllByProjectId(projectId: ProjectId)
}
