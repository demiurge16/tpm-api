package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.TeamMember
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectRole
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.TeamMemberRoleId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId

interface TeamMemberService {
    fun giveRoleToUser(userId: UserId, role: ProjectRole, projectId: ProjectId): TeamMember
    fun removeRoleFromUser(id: TeamMemberRoleId)
}
