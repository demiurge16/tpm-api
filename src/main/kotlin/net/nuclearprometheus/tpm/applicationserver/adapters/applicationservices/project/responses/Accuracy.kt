package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses

import java.util.UUID

data class Accuracy(
    val id: UUID,
    val name: String,
    val description: String,
)
