package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.handlers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.requests.ProjectMoveDeadlineRequest
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.requests.ProjectMoveStartRequest
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.responses.ProjectMoveDeadlineResponse
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.responses.ProjectMoveStartResponse
import org.springframework.stereotype.Service
import java.util.*

@Service class ProjectDatesUpdateRequestHandlerImpl : ProjectDatesUpdateRequestHandler {

    override fun moveProjectStart(id: UUID, newStart: ProjectMoveStartRequest): ProjectMoveStartResponse {
        TODO("Not yet implemented")
    }

    override fun moveProjectDeadline(id: UUID, newEnd: ProjectMoveDeadlineRequest): ProjectMoveDeadlineResponse {
        TODO("Not yet implemented")
    }
}