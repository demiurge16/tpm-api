package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses

import java.util.*

sealed class UnitResponse {

    data class Unit(
        val id: UUID,
        val name: String,
        val description: String,
        val volume: Int,
        val measurement: UnitMeasurement,
        val active: Boolean
    ) : UnitResponse()

    data class Status(
        val id: UUID,
        val active: Boolean
    ) : UnitResponse()
}
