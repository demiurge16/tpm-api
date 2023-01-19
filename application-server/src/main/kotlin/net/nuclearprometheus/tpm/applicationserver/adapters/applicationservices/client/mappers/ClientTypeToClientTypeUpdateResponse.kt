package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientTypeUpdateResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientType

fun ClientType.toUpdateResponse() = ClientTypeUpdateResponse(
    id = id.value,
    name = name,
    description = description,
    corporate = corporate,
    active = active
)
