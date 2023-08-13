package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.project

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.ProjectThreadApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectThreadRequest
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/project/{projectId}/thread")
class ProjectThreadController(private val service: ProjectThreadApplicationService) {

private val logger = loggerFor(ProjectThreadController::class.java)

    @GetMapping("")
    fun getThreads(@PathVariable(name = "projectId") projectId: UUID) = with(logger) {
        info("GET /api/v1/project/$projectId/thread")

        ResponseEntity.ok().body(service.getThreadsForProject(projectId))
    }

    @PostMapping("")
    fun createThread(@PathVariable(name = "projectId") projectId: UUID, @RequestBody request: ProjectThreadRequest.Create) =
        with(logger) {
            info("POST /api/v1/project/$projectId/thread")

            ResponseEntity.ok().body(service.createThread(projectId, request))
        }
}

