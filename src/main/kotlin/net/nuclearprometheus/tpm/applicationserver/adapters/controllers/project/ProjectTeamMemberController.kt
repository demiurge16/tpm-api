package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.project

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.ProjectTeamMemberApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectTeamMemberRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.ErrorResponse
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.project.ProjectTeamMemberAlreadyAddedException
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.project.ProjectTeamMemberAlreadyAssignedRoleException
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
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

        ResponseEntity.ok().body(service.giveRoleToUser(projectId, request))
    }

    @DeleteMapping("/{teamMemberId}")
    fun removeTeamMember(
        @PathVariable(name = "projectId") projectId: UUID,
        @PathVariable(name = "teamMemberId") teamMemberId: UUID
    ) = with(logger) {
        info("DELETE /api/v1/project/$projectId/team-member/$teamMemberId")

        service.removeRoleFromUser(projectId, teamMemberId)
        ResponseEntity.noContent().build<Void>()
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: NotFoundException) = with(logger) {
        warn("NotFoundException: ${e.message}")
        ResponseEntity.notFound().build<Void>()
    }

    @ExceptionHandler(ProjectTeamMemberAlreadyAddedException::class)
    fun handleProjectTeamMemberAlreadyAddedException(e: ProjectTeamMemberAlreadyAddedException) = with(logger) {
        warn("ProjectTeamMemberAlreadyAddedException: ${e.message}")
        ResponseEntity.badRequest().body(
            ErrorResponse(e.message ?: "Project team member already added")
        )
    }

    @ExceptionHandler(ProjectTeamMemberAlreadyAssignedRoleException::class)
    fun handleProjectTeamMemberAlreadyAssignedRoleException(e: ProjectTeamMemberAlreadyAssignedRoleException) = with(logger) {
        warn("ProjectTeamMemberAlreadyAssignedRoleException: ${e.message}")
        ResponseEntity.badRequest().body(
            ErrorResponse(e.message ?: "Project team member already assigned role")
        )
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(e: IllegalStateException) = with(logger) {
        warn("IllegalStateException: ${e.message}")
        ResponseEntity.internalServerError().body(
            ErrorResponse(e.message ?: "Illegal state")
        )
    }
}

