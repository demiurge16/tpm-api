package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientActiveStatusResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.Client

fun Client.toActiveStatusResponse() = ClientActiveStatusResponse(
    id = id.value,
    active = active
)