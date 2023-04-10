package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.task

import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/task/{taskId}")
class TaskPriorityController {

    @PatchMapping("/change-priority")
    fun changePriority(@PathVariable(name = "taskId") taskId: UUID, @RequestBody request: Any) {
        TODO()
    }
}
