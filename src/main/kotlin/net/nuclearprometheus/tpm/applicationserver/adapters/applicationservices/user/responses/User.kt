package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.responses

import java.util.*

data class User(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val roles: List<Role>
)
