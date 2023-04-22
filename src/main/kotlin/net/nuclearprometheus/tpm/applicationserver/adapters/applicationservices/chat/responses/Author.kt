package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.responses

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(name = "Author")
data class Author(
    val teamMemberId: UUID,
    val userId: UUID,
    val firstName: String,
    val lastName: String,
    val email: String,
)
