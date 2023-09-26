package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.ServiceTypeStatus
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.ServiceType as ServiceTypeResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ServiceType

object ServiceTypeMapper {
    fun ServiceType.toView() = ServiceTypeResponse(
        id = id.value,
        name = name,
        description = description,
        active = active
    )

    fun ServiceType.toActivityStatus() = ServiceTypeStatus(id = id.value, active = active)
}
