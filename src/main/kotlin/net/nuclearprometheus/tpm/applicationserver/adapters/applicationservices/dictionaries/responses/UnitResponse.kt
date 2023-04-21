package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Measurement
import java.util.*

sealed class UnitResponse {

    data class View(
        val id: UUID,
        val name: String,
        val description: String,
        val volume: Int,
        val measurement: MeasurementView,
        val active: Boolean
    ) : UnitResponse() {
        data class MeasurementView(
            val measurement: Measurement,
            val name: String,
            val description: String,
        )
    }

    data class ActivityStatus(
        val id: UUID,
        val active: Boolean
    ) : UnitResponse()

    data class MeasurementView(
        val measurement: Measurement,
        val name: String,
        val description: String,
    )
}