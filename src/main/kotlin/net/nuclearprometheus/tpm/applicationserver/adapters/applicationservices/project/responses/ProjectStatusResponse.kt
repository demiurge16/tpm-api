package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectStatus
import java.util.*

sealed class ProjectStatusResponse {

    data class NewStatus(val projectId: UUID, val status: ProjectStatusView) : ProjectStatusResponse() {

        data class ProjectStatusView(
            val status: ProjectStatus,
            val name: String,
            val description: String,
        )
    }
}