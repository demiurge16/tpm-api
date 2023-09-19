package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses

import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.ProjectRole

data class ProjectRole(
    val role: ProjectRole,
    val title: String,
    val description: String
)

