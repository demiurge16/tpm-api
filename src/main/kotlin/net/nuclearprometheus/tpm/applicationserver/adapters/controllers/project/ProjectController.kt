package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.project

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.ProjectApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectRequest
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/project")
class ProjectController(private val service: ProjectApplicationService) {

    private val logger = loggerFor(ProjectController::class.java)

    @GetMapping("")
    fun getProjects(query: ProjectRequest.List) = with(logger) {
        info("GET /api/v1/project")
        ResponseEntity.ok().body(service.getProjects(query))
    }

    @GetMapping("/{id}")
    fun getProject(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("GET /api/v1/project/$id")
        ResponseEntity.ok().body(service.getProject(id))
    }

    @PostMapping("")
    fun createProject(@RequestBody request: ProjectRequest.Create) = with(logger) {
        info("POST /api/v1/project")
        ResponseEntity.ok().body(service.createProject(request))
    }

    @PutMapping("/{id}")
    fun updateProject(@PathVariable(name = "id") id: UUID, @RequestBody request: ProjectRequest.Update) = with(logger) {
        info("PUT /api/v1/project/$id")
        ResponseEntity.ok().body(service.updateProject(id, request))
    }

    @PatchMapping("/{id}/move-start")
    fun moveStart(@PathVariable(name = "id") id: UUID, @RequestBody request: ProjectRequest.MoveStart) = with(logger) {
        info("PATCH /api/v1/project/$id/move-start")
        ResponseEntity.ok().body(service.moveProjectStart(id, request))
    }

    @PatchMapping("/{id}/move-deadline")
    fun moveDeadline(@PathVariable(name = "id") id: UUID, @RequestBody request: ProjectRequest.MoveDeadline) = with(logger) {
        info("PATCH /api/v1/project/$id/move-deadline")
        ResponseEntity.ok().body(service.moveProjectDeadline(id, request))
    }
}
