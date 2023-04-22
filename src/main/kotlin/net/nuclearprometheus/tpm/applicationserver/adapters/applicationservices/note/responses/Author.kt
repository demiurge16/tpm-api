package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.note.responses

import java.util.*

data class Author(
    val teamMemberId: UUID,
    val userId: UUID,
    val firstName: String,
    val lastName: String,
    val email: String,
)