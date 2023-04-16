package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectStatus

sealed class ProjectRefdataResponse {

    data class StatusView(
        val status: ProjectStatus,
        val name: String,
        val description: String,
    )
}