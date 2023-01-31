package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.ClientTypeUpdateRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientTypeUpdateResponse
import java.util.*

interface ClientTypeUpdateRequestHandler {
    fun updateClientType(id: UUID, request: ClientTypeUpdateRequest): ClientTypeUpdateResponse
}
