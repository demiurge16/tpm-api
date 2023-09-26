package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientTypeStatus
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientType as ClientTypeResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientType

object ClientTypeMapper {

    fun ClientType.toView() = ClientTypeResponse(
        id = id.value,
        name = name,
        description = description,
        corporate = corporate,
        active = active
    )

    fun ClientType.toActivityStatus() = ClientTypeStatus(
        id = id.value,
        active = active
    )
}
