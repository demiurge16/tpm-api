package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.project

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.ProjectApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/project")
class ProjectController(private val service: ProjectApplicationService) {

    private val logger = loggerFor(ProjectController::class.java)

    @GetMapping("")
    fun getProjects() = with(logger) {
        info("GET /api/v1/project")

        service.getProjects()
    }

    @GetMapping("/{id}")
    fun getProject(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("GET /api/v1/project/$id")

        service.getProject(id)
    }

    @PostMapping("")
    fun createProject(@RequestBody request: ProjectRequest.Create) = with(logger) {
        info("POST /api/v1/project")
        info("request: $request")

        service.createProject(request)
    }

    @PutMapping("/{id}")
    fun updateProject(@PathVariable(name = "id") id: UUID, @RequestBody request: ProjectRequest.Update) = with(logger) {
        info("PUT /api/v1/project/$id")
        info("request: $request")

        service.updateProject(id, request)
    }

    @PatchMapping("/{id}/move-start")
    fun moveStart(@PathVariable(name = "id") id: UUID, @RequestBody request: ProjectRequest.MoveStart) = with(logger) {
        info("PATCH /api/v1/project/$id/move-start")
        info("request: $request")

        service.moveProjectStart(id, request)
    }

    @PatchMapping("/{id}/move-deadline")
    fun moveDeadline(@PathVariable(name = "id") id: UUID, @RequestBody request: ProjectRequest.MoveDeadline) = with(logger) {
        info("PATCH /api/v1/project/$id/move-deadline")
        info("request: $request")

        service.moveProjectDeadline(id, request)
    }
}
