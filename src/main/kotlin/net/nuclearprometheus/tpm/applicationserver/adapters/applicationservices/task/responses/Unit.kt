package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses

import java.util.*

data class Unit(
    val id: UUID,
    val name: String,
    val description: String,
)