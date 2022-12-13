package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.handlers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.requests.ProjectUpdateRequest
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.responses.ProjectUpdateResponse
import org.springframework.stereotype.Service
import java.util.*

@Service class ProjectUpdateRequestHandlerImpl : ProjectUpdateRequestHandler {

    override fun updateProject(id: UUID, projectUpdate: ProjectUpdateRequest): ProjectUpdateResponse {
        TODO("Not yet implemented")
    }
}