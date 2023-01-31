package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses

import java.util.*

data class ClientTypeView(
    val id: UUID,
    val name: String,
    val description: String,
    val corporate: Boolean,
    val active: Boolean
)
