package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.project

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.ProjectTaskApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectTaskRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/project/{projectId}/task")
class ProjectTaskController(
    private val service: ProjectTaskApplicationService
) {

    private val logger = loggerFor(ProjectTaskController::class.java)

    @GetMapping("")
    fun getTasks(@PathVariable(name = "projectId") projectId: UUID) = with(logger) {
        info("GET /api/v1/project/$projectId/task")

        ResponseEntity.ok().body(service.getTasksForProject(projectId))
    }

    @PostMapping("")
    fun createTask(@PathVariable(name = "projectId") projectId: UUID, @RequestBody request: ProjectTaskRequest.Create) =
        with(logger) {
            info("POST /api/v1/project/$projectId/task")

            ResponseEntity.ok().body(service.createTask(projectId, request))
        }
}

