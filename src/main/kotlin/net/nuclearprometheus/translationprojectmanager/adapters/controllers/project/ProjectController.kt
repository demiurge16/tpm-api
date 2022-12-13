package net.nuclearprometheus.translationprojectmanager.adapters.controllers.project

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.ProjectApplicationService
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.requests.ProjectCreateRequest
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.requests.ProjectMoveDeadlineRequest
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.requests.ProjectMoveStartRequest
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.requests.ProjectUpdateRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/project")
class ProjectController(private val service: ProjectApplicationService) {

    @GetMapping("")
    fun getProjects() = service.getProjects()

    @GetMapping("/{id}")
    fun getProject(@PathVariable(name = "id") id: UUID) = service.getProject(id)

    @PostMapping("")
    fun createProject(@RequestBody request: ProjectCreateRequest) = service.createProject(request)

    @PutMapping("/{id}")
    fun updateProject(@PathVariable(name = "id") id: UUID, @RequestBody request: ProjectUpdateRequest) = service.updateProject(id, request)

    @PatchMapping("/{id}/move-start")
    fun moveStart(@PathVariable(name = "id") id: UUID, @RequestBody request: ProjectMoveStartRequest) = service.moveProjectStart(id, request)

    @PatchMapping("/{id}/move-deadline")
    fun moveDeadline(@PathVariable(name = "id") id: UUID, @RequestBody request: ProjectMoveDeadlineRequest) = service.moveProjectDeadline(id, request)

    @PatchMapping("/{id}/finish-draft")
    fun finishDraft(@PathVariable(name = "id") id: UUID) = service.finishDraft(id)

    @PatchMapping("/{id}/back-to-draft")
    fun backToDraft(@PathVariable(name = "id") id: UUID) = service.backToDraft(id)

    @PatchMapping("/{id}/start-progress")
    fun startProgress(@PathVariable(name = "id") id: UUID) = service.startProgress(id)

    @PatchMapping("/{id}/finish-progress")
    fun finishProgress(@PathVariable(name = "id") id: UUID) = service.finishProgress(id)

    @PatchMapping("/{id}/back-to-progress")
    fun backToProgress(@PathVariable(name = "id") id: UUID) = service.backToProgress(id)

    @PatchMapping("/{id}/deliver")
    fun deliver(@PathVariable(name = "id") id: UUID) = service.deliver(id)

    @PatchMapping("/{id}/invoice")
    fun invoice(@PathVariable(name = "id") id: UUID) = service.invoice(id)

    @PatchMapping("/{id}/pay")
    fun pay(@PathVariable(name = "id") id: UUID) = service.pay(id)
}
