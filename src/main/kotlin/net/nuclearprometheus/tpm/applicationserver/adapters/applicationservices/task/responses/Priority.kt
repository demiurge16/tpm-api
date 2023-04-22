package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses

import java.util.*

data class Priority(
    val id: UUID,
    val name: String,
    val description: String,
    val emoji: String,
    val value: Int
)
