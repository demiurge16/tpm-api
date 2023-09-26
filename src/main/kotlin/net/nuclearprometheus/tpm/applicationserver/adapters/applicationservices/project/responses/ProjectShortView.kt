package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses

import java.util.*

data class ProjectShortView(
    val id: UUID,
    val title: String,
    val status: ProjectStatus
)