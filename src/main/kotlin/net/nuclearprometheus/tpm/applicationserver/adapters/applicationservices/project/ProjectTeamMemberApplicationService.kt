package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectTeamMemberRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers.ProjectTeamMemberResponseMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.TeamMember
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.teammember.TeamMemberService
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.emptyPage
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.singlePage
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProjectTeamMemberApplicationService(
    private val teamMemberService: TeamMemberService,
    private val teamMemberRepository: TeamMemberRepository,
    private val projectRepository: ProjectRepository
) {

    private val logger = loggerFor(ProjectTeamMemberApplicationService::class.java)

    fun getTeamMembers(projectId: UUID): Page<TeamMember> {
        logger.info("getTeamMembers($projectId)")

        val project = projectRepository.get(ProjectId(projectId))
        if (project == null) {
            logger.warn("Project with id $projectId not found")
            return emptyPage()
        }

        return singlePage(teamMemberRepository.getAllByProjectId(ProjectId(projectId))).map { it.toView(project) }
    }

    fun addTeamMember(projectId: UUID, request: ProjectTeamMemberRequest.Create): TeamMember {
        logger.info("addTeamMember($projectId, $request)")
        return teamMemberService.create(UserId(request.userId), request.role, ProjectId(projectId))
            .let {
                val project = projectRepository.get(ProjectId(projectId))

                if (project == null) {
                    logger.warn("Project with id $projectId not found")
                    throw IllegalStateException("Project with id $projectId not found")
                }

                it.toView(project)
            }
    }

    fun removeTeamMember(projectId: UUID, teamMemberId: UUID) {
        logger.info("removeTeamMember($projectId, $teamMemberId)")
        teamMemberService.delete(TeamMemberId(projectId))
    }
}