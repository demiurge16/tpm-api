package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Measurement

data class UnitMeasurement(
    val code: Measurement,
    val name: String,
    val description: String,
)
