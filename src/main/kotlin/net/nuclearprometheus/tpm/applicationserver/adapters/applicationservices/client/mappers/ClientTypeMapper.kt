package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientTypeResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientType

object ClientTypeMapper {

    fun ClientType.toView() = ClientTypeResponse.ClientType(
        id = id.value,
        name = name,
        description = description,
        corporate = corporate,
        active = active
    )

    fun ClientType.toActivityStatus() = ClientTypeResponse.Status(
        id = id.value,
        active = active
    )
}
