package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.teammember

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.project.ProjectTeamMemberAlreadyAddedException
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRoleRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.logging.Logger
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project.security.ProjectPermissionService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project.security.getGrantedScopes

class TeamMemberServiceImpl(
    private val teamMemberRoleRepository: TeamMemberRoleRepository,
    private val teamMemberRepository: TeamMemberRepository,
    private val userRepository: UserRepository,
    private val projectRepository: ProjectRepository,
    private val projectPermissionService: ProjectPermissionService,
    private val logger: Logger
) : TeamMemberService {

    override fun giveRoleToUser(userId: UserId, role: ProjectRole, projectId: ProjectId): TeamMember {
        logger.info("Creating team member for user $userId with role $role for project $projectId")

        val project = projectRepository.get(projectId) ?: throw NotFoundException("Project does not exist")
        val user = userRepository.get(userId) ?: throw NotFoundException("User does not exist")

        if (project.teamMembers.any { teamMember -> teamMember.user.id == userId && teamMember.roles.map { it.role }.contains(role) }) {
            throw ProjectTeamMemberAlreadyAddedException("User is already a team member of this project")
        }

        teamMemberRoleRepository.create(
            TeamMemberRole(
                role = role,
                userId = userId,
                projectId = projectId
            )
        )

        role.getGrantedScopes().forEach {
            val result = projectPermissionService.grantUserProjectPermission(user, project, it)
            if (result.isFailure) {
                throw result.exceptionOrNull()!!
            }
        }

        return teamMemberRepository.get(TeamMemberId(userId.value)) ?: throw NotFoundException("Team member not found")
    }

    override fun removeRoleFromUser(id: TeamMemberRoleId) {
        val teamMemberRole = teamMemberRoleRepository.get(id) ?: return
        val teamMember = teamMemberRepository.get(TeamMemberId(teamMemberRole.userId.value)) ?: return
        val project = projectRepository.get(teamMemberRole.projectId) ?: return

        val permissionsToRevoke = teamMemberRole.role.getGrantedScopes().toMutableSet()

        teamMember.roles.forEach {
            permissionsToRevoke.removeAll(it.role.getGrantedScopes())
        }

        permissionsToRevoke.forEach {
            val result = projectPermissionService.revokeUserProjectPermission(teamMember.user, project, it)
            if (result.isFailure) {
                throw result.exceptionOrNull()!!
            }
        }

        teamMemberRoleRepository.delete(id)
    }
}