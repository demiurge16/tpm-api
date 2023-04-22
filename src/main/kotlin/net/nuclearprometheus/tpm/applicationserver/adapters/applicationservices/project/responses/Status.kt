package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectStatus

data class Status(
    val status: ProjectStatus,
    val name: String,
    val description: String,
)