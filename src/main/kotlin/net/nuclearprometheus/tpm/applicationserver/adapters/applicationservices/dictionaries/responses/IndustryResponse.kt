package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses

import java.util.*

sealed class IndustryResponse {

    data class Industry(
        val id: UUID,
        val name: String,
        val description: String,
        val active: Boolean,
    ) : IndustryResponse()

    data class Status(val id: UUID, val active: Boolean) : IndustryResponse()
}