package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.project

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.ProjectChatApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectChatRequest
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/project/{projectId}/chat")
class ProjectChatController(
    private val service: ProjectChatApplicationService
) {

    private val logger = loggerFor(ProjectChatController::class.java)

    @GetMapping("")
    fun getChats(@PathVariable(name = "projectId") projectId: UUID) = with(logger) {
        info("GET /api/v1/project/$projectId/chat")

        service.getChatsForProject(projectId)
    }

    @PostMapping("")
    fun createChat(@PathVariable(name = "projectId") projectId: UUID, @RequestBody request: ProjectChatRequest.Create) =
        with(logger) {
            info("POST /api/v1/project/$projectId/chat")
            info("request: $request")

            service.createChat(projectId, request)
        }
}

