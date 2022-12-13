package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.handlers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.requests.ProjectMoveDeadlineRequest
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.requests.ProjectMoveStartRequest
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.responses.ProjectMoveDeadlineResponse
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.responses.ProjectMoveStartResponse
import java.util.*

interface ProjectDatesUpdateRequestHandler {
    fun moveProjectStart(id: UUID, newStart: ProjectMoveStartRequest): ProjectMoveStartResponse
    fun moveProjectDeadline(id: UUID, newEnd: ProjectMoveDeadlineRequest): ProjectMoveDeadlineResponse
}
