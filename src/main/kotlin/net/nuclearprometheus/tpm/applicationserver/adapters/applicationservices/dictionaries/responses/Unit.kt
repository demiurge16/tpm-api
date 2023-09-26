package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses

import java.util.*

data class Unit(
    val id: UUID,
    val name: String,
    val description: String,
    val volume: Int,
    val measurement: UnitMeasurement,
    val active: Boolean
)
