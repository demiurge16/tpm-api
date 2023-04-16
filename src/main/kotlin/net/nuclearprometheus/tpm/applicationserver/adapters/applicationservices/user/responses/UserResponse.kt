package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.responses

import java.util.*

sealed class UserResponse {
    data class View(
        val id: UUID,
        val firstName: String,
        val lastName: String,
        val username: String,
        val email: String
    ) : UserResponse()
}