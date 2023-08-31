package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses

import java.util.*

data class Author(
    val userId: UUID,
    val firstName: String,
    val lastName: String,
    val email: String
)
