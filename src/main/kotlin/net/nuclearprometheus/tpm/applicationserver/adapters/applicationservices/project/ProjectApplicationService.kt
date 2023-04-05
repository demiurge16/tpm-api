package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.handlers.*
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectCreateRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectMoveDeadlineRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectMoveStartRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectUpdateRequest
import org.springframework.stereotype.Service
import java.util.*

@Service class ProjectApplicationService(
    private val projectViewRequestHandler: ProjectViewRequestHandler,
    private val projectCreateRequestHandler: ProjectCreateRequestHandler,
    private val projectUpdateRequestHandler: ProjectUpdateRequestHandler,
    private val projectDatesUpdateRequestHandler: ProjectDatesUpdateRequestHandler,
    private val projectStatusUpdateRequestHandler: ProjectStatusUpdateRequestHandler
) {

    fun getProjects() = projectViewRequestHandler.getProjects()
    fun getProject(id: UUID)= projectViewRequestHandler.getProject(id)
    fun createProject(request: ProjectCreateRequest) = projectCreateRequestHandler.createProject(request)
    fun updateProject(id: UUID, request: ProjectUpdateRequest) = projectUpdateRequestHandler.updateProject(id, request)
    fun moveProjectStart(id: UUID, start: ProjectMoveStartRequest) = projectDatesUpdateRequestHandler.moveProjectStart(id, start)
    fun moveProjectDeadline(id: UUID, end: ProjectMoveDeadlineRequest) = projectDatesUpdateRequestHandler.moveProjectDeadline(id, end)
    fun finishDraft(id: UUID) =  projectStatusUpdateRequestHandler.finishDraft(id)
    fun backToDraft(id: UUID) =  projectStatusUpdateRequestHandler.backToDraft(id)
    fun startProgress(id: UUID) = projectStatusUpdateRequestHandler.startProgress(id)
    fun finishProgress(id: UUID) = projectStatusUpdateRequestHandler.finishProgress(id)
    fun backToProgress(id: UUID) = projectStatusUpdateRequestHandler.backToProgress(id)
    fun deliver(id: UUID) = projectStatusUpdateRequestHandler.deliver(id)
    fun invoice(id: UUID) = projectStatusUpdateRequestHandler.invoice(id)
    fun pay(id: UUID) = projectStatusUpdateRequestHandler.pay(id)
}