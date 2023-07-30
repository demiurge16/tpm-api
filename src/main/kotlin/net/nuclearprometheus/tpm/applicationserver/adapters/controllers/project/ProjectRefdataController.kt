package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.project

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.ProjectRefdataApplicationService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/project/refdata")
class ProjectRefdataController(private val service: ProjectRefdataApplicationService) {

    private val logger = loggerFor(ProjectRefdataController::class.java)

    @GetMapping("/status")
    fun getStatuses() = with(logger) {
        info("GET /api/v1/project/refdata/status")

        ResponseEntity.ok().body(service.getStatuses())
    }

    @GetMapping("/team-member/role")
    fun getTeamMemberRoles() = with(logger) {
        info("GET /api/v1/project/refdata/team-member/role")

        ResponseEntity.ok().body(service.getTeamMemberRoles())
    }
}
