package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectStatus

data class ProjectStatus(
    val status: ProjectStatus,
    val title: String,
    val description: String,
)