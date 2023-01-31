package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.ClientCreateRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.ClientUpdateRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientActiveStatusResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientCreateResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientUpdateResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientView
import java.util.*

interface ClientApplicationService {
    fun getClientTypes(): List<ClientView>
    fun getClientType(id: UUID): ClientView
    fun createClientType(request: ClientCreateRequest): ClientCreateResponse
    fun updateClientType(id: UUID, request: ClientUpdateRequest): ClientUpdateResponse
    fun activate(id: UUID): ClientActiveStatusResponse
    fun deactivate(id: UUID): ClientActiveStatusResponse
}