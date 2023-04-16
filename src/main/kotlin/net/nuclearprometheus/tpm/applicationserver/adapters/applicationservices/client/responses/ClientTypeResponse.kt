package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses

import java.util.*

sealed class ClientTypeResponse {
    data class View(
        val id: UUID,
        val name: String,
        val description: String,
        val corporate: Boolean,
        val active: Boolean
    )

    data class ActivityStatus(
        val id: UUID,
        val active: Boolean
    )
}
