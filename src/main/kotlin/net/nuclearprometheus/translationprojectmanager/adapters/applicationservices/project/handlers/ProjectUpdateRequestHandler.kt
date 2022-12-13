package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.handlers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.requests.ProjectUpdateRequest
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.responses.ProjectUpdateResponse
import java.util.*

interface ProjectUpdateRequestHandler {
    fun updateProject(id: UUID, projectUpdate: ProjectUpdateRequest): ProjectUpdateResponse
}
