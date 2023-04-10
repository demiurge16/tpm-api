package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.project

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.ProjectApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectCreateRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectMoveDeadlineRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectMoveStartRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectUpdateRequest
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/project")
class ProjectCrudController(private val service: ProjectApplicationService) {

    @GetMapping("")
    fun getProjects() = service.getProjects()

    @GetMapping("/{id}")
    fun getProject(@PathVariable(name = "id") id: UUID) = service.getProject(id)

    @PostMapping("")
    fun createProject(@RequestBody request: ProjectCreateRequest) = service.createProject(request)

    @PutMapping("/{id}")
    fun updateProject(@PathVariable(name = "id") id: UUID, @RequestBody request: ProjectUpdateRequest) =
        service.updateProject(id, request)

    @PatchMapping("/{id}/move-start")
    fun moveStart(@PathVariable(name = "id") id: UUID, @RequestBody request: ProjectMoveStartRequest) =
        service.moveProjectStart(id, request)

    @PatchMapping("/{id}/move-deadline")
    fun moveDeadline(@PathVariable(name = "id") id: UUID, @RequestBody request: ProjectMoveDeadlineRequest) =
        service.moveProjectDeadline(id, request)
}

