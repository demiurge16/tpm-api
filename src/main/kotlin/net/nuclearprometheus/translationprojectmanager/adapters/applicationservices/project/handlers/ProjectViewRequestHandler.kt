package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.handlers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.responses.ProjectViewResponse
import java.util.*

interface ProjectViewRequestHandler {
    fun getProjects(): List<ProjectViewResponse>
    fun getProject(id: UUID): ProjectViewResponse
}
