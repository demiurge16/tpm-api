package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.handlers.*
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectCreateRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectMoveDeadlineRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectMoveStartRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectUpdateRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.controllers.project.*
import org.springframework.stereotype.Service
import java.util.*

@Service class ProjectApplicationServiceImpl(
    private val projectViewRequestHandler: ProjectViewRequestHandler,
    private val projectCreateRequestHandler: ProjectCreateRequestHandler,
    private val projectUpdateRequestHandler: ProjectUpdateRequestHandler,
    private val projectDatesUpdateRequestHandler: ProjectDatesUpdateRequestHandler,
    private val projectStatusUpdateRequestHandler: ProjectStatusUpdateRequestHandler
) : ProjectApplicationService {

    override fun getProjects() = projectViewRequestHandler.getProjects()

    override fun getProject(id: UUID)= projectViewRequestHandler.getProject(id)

    override fun createProject(request: ProjectCreateRequest) = projectCreateRequestHandler.createProject(request)

    override fun updateProject(id: UUID, request: ProjectUpdateRequest) = projectUpdateRequestHandler.updateProject(id, request)

    override fun moveProjectStart(id: UUID, start: ProjectMoveStartRequest) = projectDatesUpdateRequestHandler.moveProjectStart(id, start)

    override fun moveProjectDeadline(id: UUID, end: ProjectMoveDeadlineRequest) = projectDatesUpdateRequestHandler.moveProjectDeadline(id, end)

    override fun finishDraft(id: UUID) =  projectStatusUpdateRequestHandler.finishDraft(id)

    override fun backToDraft(id: UUID) =  projectStatusUpdateRequestHandler.backToDraft(id)

    override fun startProgress(id: UUID) = projectStatusUpdateRequestHandler.startProgress(id)

    override fun finishProgress(id: UUID) = projectStatusUpdateRequestHandler.finishProgress(id)

    override fun backToProgress(id: UUID) = projectStatusUpdateRequestHandler.backToProgress(id)

    override fun deliver(id: UUID) = projectStatusUpdateRequestHandler.deliver(id)

    override fun invoice(id: UUID) = projectStatusUpdateRequestHandler.invoice(id)

    override fun pay(id: UUID) = projectStatusUpdateRequestHandler.pay(id)
}