package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses

import java.util.*

sealed class PriorityResponse {

    data class Priority(
        val id: UUID,
        val name: String,
        val description: String,
        val emoji: String,
        val value: Int,
        val active: Boolean
    ) : PriorityResponse()

    data class Status(
        val id: UUID,
        val active: Boolean
    ) : PriorityResponse()
}