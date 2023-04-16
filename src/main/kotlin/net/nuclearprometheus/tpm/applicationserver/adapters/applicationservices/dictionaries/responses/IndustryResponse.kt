package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses

import java.util.*

sealed class IndustryResponse {

    data class View(
        val id: UUID,
        val name: String,
        val description: String,
        val active: Boolean,
    ) : IndustryResponse()

    data class ActivityStatus(val id: UUID, val active: Boolean) : IndustryResponse()
}