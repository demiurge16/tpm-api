package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.project

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.ProjectTeamMemberApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectTeamMemberRequest
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/project/{projectId}/team-member")
class ProjectTeamMemberController(
    private val service: ProjectTeamMemberApplicationService
) {

    private val logger = loggerFor(ProjectTeamMemberController::class.java)

    @GetMapping("")
    fun getTeamMembers(@PathVariable(name = "projectId") projectId: UUID) = with(logger) {
        info("GET /api/v1/project/$projectId/team-member")

        ResponseEntity.ok().body(service.getTeamMembers(projectId))
    }

    @PostMapping("")
    fun addTeamMember(
        @PathVariable(name = "projectId") projectId: UUID,
        @RequestBody request: ProjectTeamMemberRequest.Create
    ) = with(logger) {
        info("POST /api/v1/project/$projectId/team-member")

        ResponseEntity.ok().body(service.addTeamMember(projectId, request))
    }

    @DeleteMapping("/{teamMemberId}")
    fun removeTeamMember(
        @PathVariable(name = "projectId") projectId: UUID,
        @PathVariable(name = "teamMemberId") teamMemberId: UUID
    ) = with(logger) {
        info("DELETE /api/v1/project/$projectId/team-member/$teamMemberId")

        service.removeTeamMember(projectId, teamMemberId)
        ResponseEntity.noContent().build<Void>()
    }
}

