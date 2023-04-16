package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses

import java.util.*

sealed class AccuracyResponse {
    data class View(
        val id: UUID,
        val name: String,
        val description: String,
        val active: Boolean
    ) : AccuracyResponse()

    data class ActivityStatus(
        val id: UUID,
        val active: Boolean
    ) : AccuracyResponse()
}