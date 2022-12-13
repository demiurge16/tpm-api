package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.handlers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.requests.ClientTypeUpdateRequest
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.responses.ClientTypeUpdateResponse
import java.util.*

interface ClientTypeUpdateRequestHandler {
    fun updateClientType(id: UUID, request: ClientTypeUpdateRequest): ClientTypeUpdateResponse
}
