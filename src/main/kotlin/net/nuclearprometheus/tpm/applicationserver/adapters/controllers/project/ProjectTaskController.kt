package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.project

import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/project/{projectId}/task")
class ProjectTaskController {

    @GetMapping("")
    fun getTasks(@PathVariable(name = "projectId") projectId: UUID) {
        TODO()
    }

    @PostMapping("")
    fun createTask(@PathVariable(name = "projectId") projectId: UUID, @RequestBody request: Any) {
        TODO()
    }
}