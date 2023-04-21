package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.UnitResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Unit

object UnitMapper {

    fun Unit.toView() = UnitResponse.View(
        id = id.value,
        name = name,
        description = description,
        volume = volume,
        measurement = UnitResponse.View.MeasurementView(
            measurement = measurement,
            name = measurement.name,
            description = measurement.description
        ),
        active = active
    )

    fun Unit.toActivityStatus() = UnitResponse.ActivityStatus(
        id = id.value,
        active = active
    )
}
