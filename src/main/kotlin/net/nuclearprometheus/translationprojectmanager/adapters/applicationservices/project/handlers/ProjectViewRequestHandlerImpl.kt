package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.handlers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.responses.ProjectViewResponse
import org.springframework.stereotype.Service
import java.util.*

@Service class ProjectViewRequestHandlerImpl : ProjectViewRequestHandler {

    override fun getProjects(): List<ProjectViewResponse> {
        TODO("Not yet implemented")
    }

    override fun getProject(id: UUID): ProjectViewResponse {
        TODO("Not yet implemented")
    }
}