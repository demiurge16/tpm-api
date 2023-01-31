package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectCreateRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectCreateResponse
import org.springframework.stereotype.Service

@Service class ProjectCreateRequestHandlerImpl : ProjectCreateRequestHandler {

    override fun createProject(projectCreate: ProjectCreateRequest): ProjectCreateResponse {
        TODO("Not yet implemented")
    }
}