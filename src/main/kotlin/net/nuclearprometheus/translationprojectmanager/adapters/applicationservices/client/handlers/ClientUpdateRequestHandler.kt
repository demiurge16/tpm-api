package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.handlers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.requests.ClientUpdateRequest
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.responses.ClientUpdateResponse
import java.util.UUID

interface ClientUpdateRequestHandler {
    fun update(id: UUID, request: ClientUpdateRequest): ClientUpdateResponse
}
