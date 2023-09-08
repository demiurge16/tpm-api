package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.teammember

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.project.ProjectTeamMemberAlreadyAddedException
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.project.ProjectTeamMemberAlreadyAssignedRoleException
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMember
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberRole
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.logging.Logger
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project.security.ProjectPermissionService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project.security.getGrantedScopes

class TeamMemberServiceImpl(
    private val teamMemberRepository: TeamMemberRepository,
    private val userRepository: UserRepository,
    private val projectRepository: ProjectRepository,
    private val projectPermissionService: ProjectPermissionService,
    private val logger: Logger
) : TeamMemberService {

    override fun create(userId: UserId, role: TeamMemberRole, projectId: ProjectId): TeamMember {
        logger.info("Creating team member for user $userId with role $role for project $projectId")

        val project = projectRepository.get(projectId) ?: throw NotFoundException("Project does not exist")

        if (project.teamMembers.any { it.user.id == userId && it.role == role }) {
            throw ProjectTeamMemberAlreadyAddedException("User is already a team member of this project")
        }

        val teamMember = TeamMember(
            user = userRepository.get(userId) ?: throw NotFoundException("User does not exist"),
            role = role,
            projectId = projectId
        )

        role.getGrantedScopes().forEach {
            val result = projectPermissionService.grantUserProjectPermission(teamMember.user, project, it)
            if (result.isFailure) {
                throw result.exceptionOrNull()!!
            }
        }

        return teamMemberRepository.create(teamMember)
    }

    override fun delete(id: TeamMemberId) {
        val teamMember = teamMemberRepository.get(id) ?: return
        val project = projectRepository.get(teamMember.projectId) ?: return

        val permissionsToRevoke = teamMember.role.getGrantedScopes().toMutableSet()

        project.teamMembers.forEach {
            if (it.user.id == teamMember.user.id) {
                permissionsToRevoke.removeAll(it.role.getGrantedScopes())
            }
        }

        permissionsToRevoke.forEach {
            val result = projectPermissionService.revokeUserProjectPermission(teamMember.user, project, it)
            if (result.isFailure) {
                throw result.exceptionOrNull()!!
            }
        }

        teamMemberRepository.delete(id)
    }
}