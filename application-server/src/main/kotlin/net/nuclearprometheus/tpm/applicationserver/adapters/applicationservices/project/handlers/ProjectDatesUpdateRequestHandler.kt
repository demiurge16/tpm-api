package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectMoveDeadlineRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectMoveStartRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectMoveDeadlineResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectMoveStartResponse
import java.util.*

interface ProjectDatesUpdateRequestHandler {
    fun moveProjectStart(id: UUID, newStart: ProjectMoveStartRequest): ProjectMoveStartResponse
    fun moveProjectDeadline(id: UUID, newEnd: ProjectMoveDeadlineRequest): ProjectMoveDeadlineResponse
}
