package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.teammember

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMember
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberRole
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository

class TeamMemberServiceImpl(
    private val teamMemberRepository: TeamMemberRepository,
    private val userRepository: UserRepository,
    private val projectRepository: ProjectRepository,
) : TeamMemberService {

    override fun create(userId: UserId, role: TeamMemberRole, projectId: ProjectId): TeamMember {
        projectRepository.get(projectId) ?: throw IllegalArgumentException("Project does not exist")

        val teamMember = TeamMember(
            user = userRepository.get(userId) ?: throw IllegalArgumentException("User does not exist"),
            role = role,
            projectId = projectId
        )

        return teamMemberRepository.create(teamMember)
    }

    override fun delete(id: TeamMemberId) {
        teamMemberRepository.delete(id)
    }

    override fun changeRole(id: TeamMemberId, role: TeamMemberRole) {
        teamMemberRepository.get(id)?.changeRole(role)
    }
}