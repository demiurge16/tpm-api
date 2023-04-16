package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project

import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.singlePage
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectTeamMemberRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers.ProjectTeamMemberResponseMapper.toView
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.teammember.TeamMemberService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProjectTeamMemberApplicationService(
    private val teamMemberService: TeamMemberService,
    private val teamMemberRepository: TeamMemberRepository
) {

    private val logger = loggerFor(ProjectTeamMemberApplicationService::class.java)

    fun getTeamMembers(projectId: UUID) = with(logger) {
        info("getTeamMembers($projectId)")

        singlePage(teamMemberRepository.getAllByProjectId(ProjectId(projectId))).map { it.toView() }
    }

    fun addTeamMember(projectId: UUID, request: ProjectTeamMemberRequest.Create) = with(logger) {
        info("addTeamMember($projectId, $request)")

        teamMemberService.create(UserId(request.userId), request.role, ProjectId(projectId)).toView()
    }

    fun removeTeamMember(projectId: UUID, teamMemberId: UUID) = with(logger) {
        info("removeTeamMember($projectId, $teamMemberId)")

        teamMemberService.delete(TeamMemberId(projectId))
    }
}