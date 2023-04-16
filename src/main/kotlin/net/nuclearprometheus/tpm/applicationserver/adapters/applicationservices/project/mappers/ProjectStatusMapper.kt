package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectStatusResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project

object ProjectStatusMapper {

    fun Project.toProjectStatusResponse() = ProjectStatusResponse.NewStatus(
        projectId = id.value,
        status = ProjectStatusResponse.NewStatus.ProjectStatusView(
            status = status,
            name = status.name,
            description = status.shortDescription
        )
    )
}