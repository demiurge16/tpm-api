package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.response

import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberRole

data class Role(
    val role: TeamMemberRole,
    val title: String,
    val description: String
)
