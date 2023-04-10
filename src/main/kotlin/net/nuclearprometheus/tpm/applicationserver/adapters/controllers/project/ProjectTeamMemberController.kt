package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.project

import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/project/{projectId}/team-member")
class ProjectTeamMemberController {

    @GetMapping("")
    fun getTeamMembers(@PathVariable(name = "projectId") projectId: UUID) {
        TODO()
    }

    @PostMapping("")
    fun addTeamMember(@PathVariable(name = "projectId") projectId: UUID, @RequestBody request: Any) {
        TODO()
    }

    @DeleteMapping("/{userId}")
    fun removeTeamMember(@PathVariable(name = "projectId") projectId: UUID, @PathVariable(name = "userId") userId: UUID) {
        TODO()
    }
}