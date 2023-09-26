package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.UnitMeasurement
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.Unit as UnitResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.UnitStatus
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Unit

object UnitMapper {

    fun Unit.toView() = UnitResponse(
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

    fun Unit.toActivityStatus() = UnitStatus(
        id = id.value,
        active = active
    )
}
