package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.file.responses

import java.util.*

data class Uploader(
    val userId: UUID,
    val firstName: String,
    val lastName: String,
    val email: String,
)