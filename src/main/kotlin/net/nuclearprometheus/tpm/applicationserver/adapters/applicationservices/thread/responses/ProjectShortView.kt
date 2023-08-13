package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses

import java.util.*

data class ProjectShortView(
    val id: UUID,
    val title: String,
    val status: ProjectStatus
)
