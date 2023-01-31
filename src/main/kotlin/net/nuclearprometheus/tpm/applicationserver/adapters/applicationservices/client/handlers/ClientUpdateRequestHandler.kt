package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.ClientUpdateRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientUpdateResponse
import java.util.UUID

interface ClientUpdateRequestHandler {
    fun update(id: UUID, request: ClientUpdateRequest): ClientUpdateResponse
}
