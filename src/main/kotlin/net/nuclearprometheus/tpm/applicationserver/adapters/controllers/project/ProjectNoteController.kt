package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.project

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.ProjectNoteApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectNoteRequest
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/project/{projectId}/note")
class ProjectNoteController(private val service: ProjectNoteApplicationService) {

    private val logger = loggerFor(ProjectNoteController::class.java)

    @GetMapping("")
    fun getNotes(@PathVariable(name = "projectId") projectId: UUID) = with(logger) {
        info("GET /api/v1/project/$projectId/note")

        service.getNotesForProject(projectId)
    }

    @PostMapping("")
    fun createNote(@PathVariable(name = "projectId") projectId: UUID, @RequestBody request: ProjectNoteRequest.Create) =
        with(logger) {
            info("POST /api/v1/project/$projectId/note")
            info("request: $request")

            service.createNote(projectId, request)
        }
}

