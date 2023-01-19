package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientTypeActiveStatusResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientType

fun ClientType.toActiveStatusResponse() = ClientTypeActiveStatusResponse(
    id = id.value,
    active = active
)
