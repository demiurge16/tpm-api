package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.responses

import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.ProjectRole

data class Role(
    val role: ProjectRole,
    val title: String,
    val description: String
)
