package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectUpdateRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectUpdateResponse
import org.springframework.stereotype.Service
import java.util.*

@Service class ProjectUpdateRequestHandlerImpl : ProjectUpdateRequestHandler {

    override fun updateProject(id: UUID, projectUpdate: ProjectUpdateRequest): ProjectUpdateResponse {
        TODO("Not yet implemented")
    }
}