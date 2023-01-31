package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectCreateRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectCreateResponse

interface ProjectCreateRequestHandler {
    fun createProject(projectCreate: ProjectCreateRequest): ProjectCreateResponse
}
