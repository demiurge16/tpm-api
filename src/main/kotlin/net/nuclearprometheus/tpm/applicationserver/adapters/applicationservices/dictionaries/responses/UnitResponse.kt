package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses

import java.util.*

sealed class UnitResponse {

    data class View(
        val id: UUID,
        val name: String,
        val description: String,
        val active: Boolean
    ) : UnitResponse()

    data class ActivityStatus(
        val id: UUID,
        val active: Boolean
    ) : UnitResponse()
}