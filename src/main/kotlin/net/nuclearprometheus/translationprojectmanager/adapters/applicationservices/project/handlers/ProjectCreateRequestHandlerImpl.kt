package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.handlers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.requests.ProjectCreateRequest
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.responses.ProjectCreateResponse
import org.springframework.stereotype.Service

@Service class ProjectCreateRequestHandlerImpl : ProjectCreateRequestHandler {

    override fun createProject(projectCreate: ProjectCreateRequest): ProjectCreateResponse {
        TODO("Not yet implemented")
    }
}