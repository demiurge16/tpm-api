package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.project.ProjectAccessException
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.project.ProjectTeamMemberAlreadyHasRoleException
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserRole
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.TeamMemberRoleRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.logging.Logger
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider

class TeamMemberServiceImpl(
    private val teamMemberRoleRepository: TeamMemberRoleRepository,
    private val teamMemberRepository: TeamMemberRepository,
    private val userRepository: UserRepository,
    private val userContextProvider: UserContextProvider,
    private val projectRepository: ProjectRepository,
    private val logger: Logger
) : TeamMemberService {

    override fun giveRoleToUser(userId: UserId, role: ProjectRole, projectId: ProjectId): TeamMember {
        logger.info("Creating team member for user $userId with role $role for project $projectId")

        val currentUser = userContextProvider.getCurrentUser()
        val project = projectRepository.get(projectId) ?: throw NotFoundException("Project does not exist")
        userRepository.get(userId) ?: throw NotFoundException("User does not exist")

        if (!currentUser.hasRole(UserRole.ADMIN) && !project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER)) {
            throw ProjectAccessException("Only admin or project manager can add team members and assign project roles")
        }

        if (project.hasTeamMemberWithRole(userId, role)) {
            throw ProjectTeamMemberAlreadyHasRoleException("User $userId already has role $role for project $projectId")
        }

        teamMemberRoleRepository.create(
            TeamMemberRole(
                role = role,
                userId = userId,
                projectId = projectId
            )
        )

        return teamMemberRepository.get(TeamMemberId(userId.value)) ?: throw NotFoundException("Team member not found")
    }

    override fun removeRoleFromUser(id: TeamMemberRoleId) {
        logger.info("Removing team member role $id")
        teamMemberRoleRepository.delete(id)
    }
}