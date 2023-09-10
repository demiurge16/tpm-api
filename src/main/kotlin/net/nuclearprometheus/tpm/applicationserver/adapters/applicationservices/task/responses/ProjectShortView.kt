package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses

import java.util.*

data class ProjectShortView(
    val id: UUID,
    val title: String,
    val status: ProjectStatus
)
