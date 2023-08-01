package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.response

import java.util.*

data class ProjectShortView(
    val id: UUID,
    val title: String,
    val status: ProjectStatus
)