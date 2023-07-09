package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.UnitMeasurement
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.UnitResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Unit

object UnitMapper {

    fun Unit.toView() = UnitResponse.Unit(
        id = id.value,
        name = name,
        description = description,
        volume = volume,
        measurement = UnitMeasurement(
            code = measurement,
            name = measurement.title,
            description = measurement.description
        ),
        active = active
    )

    fun Unit.toActivityStatus() = UnitResponse.Status(
        id = id.value,
        active = active
    )
}
