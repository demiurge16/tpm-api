package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.requests.*
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.responses.*
import java.util.UUID

interface ProjectApplicationService {
    fun getProjects(): List<ProjectViewResponse>
    fun getProject(id: UUID): ProjectViewResponse
    fun createProject(request: ProjectCreateRequest): ProjectCreateResponse
    fun updateProject(id: UUID, request: ProjectUpdateRequest): ProjectUpdateResponse
    fun moveProjectStart(id: UUID, start: ProjectMoveStartRequest): ProjectMoveStartResponse
    fun moveProjectDeadline(id: UUID, end: ProjectMoveDeadlineRequest): ProjectMoveDeadlineResponse
    fun finishDraft(id: UUID): ProjectStatusUpdateResponse
    fun backToDraft(id: UUID): ProjectStatusUpdateResponse
    fun startProgress(id: UUID): ProjectStatusUpdateResponse
    fun finishProgress(id: UUID): ProjectStatusUpdateResponse
    fun backToProgress(id: UUID): ProjectStatusUpdateResponse
    fun deliver(id: UUID): ProjectStatusUpdateResponse
    fun invoice(id: UUID): ProjectStatusUpdateResponse
    fun pay(id: UUID): ProjectStatusUpdateResponse
}
