package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses

import java.util.*

data class ClientTypeStatus(
    val id: UUID,
    val active: Boolean
)
