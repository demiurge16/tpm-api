package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.ClientTypeCreateRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientTypeCreateResponse


interface ClientTypeCreateRequestHandler {
    fun createClientType(request: ClientTypeCreateRequest): ClientTypeCreateResponse
}
