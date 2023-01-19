package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectUpdateRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectUpdateResponse
import java.util.*

interface ProjectUpdateRequestHandler {
    fun updateProject(id: UUID, projectUpdate: ProjectUpdateRequest): ProjectUpdateResponse
}
