package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.ServiceTypeResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ServiceType

object ServiceTypeMapper {
    fun ServiceType.toView() = ServiceTypeResponse.ServiceType(
        id = id.value,
        name = name,
        description = description,
        active = active
    )

    fun ServiceType.toActivityStatus() = ServiceTypeResponse.Status(id = id.value, active = active)
}
