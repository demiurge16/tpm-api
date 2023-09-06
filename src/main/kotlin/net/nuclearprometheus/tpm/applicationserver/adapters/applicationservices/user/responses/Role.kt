package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.responses

import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserRole

data class Role(
    val role: UserRole,
    val tag: String,
    val title: String,
    val description: String
)
