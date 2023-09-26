package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectNewStatus
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectStatus
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project

object ProjectStatusMapper {

    fun Project.toProjectStatusResponse() = ProjectNewStatus(
        projectId = id.value,
        status = ProjectStatus(
            status = status,
            title = status.title,
            description = status.description
        )
    )
}
