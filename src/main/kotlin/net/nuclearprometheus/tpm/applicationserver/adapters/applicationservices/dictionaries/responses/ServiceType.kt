package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses

import java.util.*

data class ServiceType(
    val id: UUID,
    val name: String,
    val description: String,
    val active: Boolean
)
