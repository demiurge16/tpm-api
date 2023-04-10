package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.task

import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/task/{taskId}")
class TaskAssignmentController {

    @PatchMapping("/assign-team-member")
    fun assignTeamMember(@PathVariable(name = "taskId") taskId: UUID, @RequestBody request: Any) {
        TODO()
    }

    @PatchMapping("/unassign-team-member")
    fun unassignTeamMember(@PathVariable(name = "taskId") taskId: UUID) {
        TODO()
    }
}
