package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.ClientCreateRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.ClientUpdateRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientActiveStatusResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientCreateResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientUpdateResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientView
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.Page
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.Client
import java.util.*

interface ClientApplicationService {
    fun getClients(query: FilteredRequest<Client>): Page<ClientView>
    fun getClient(id: UUID): ClientView
    fun createClient(request: ClientCreateRequest): ClientCreateResponse
    fun updateClient(id: UUID, request: ClientUpdateRequest): ClientUpdateResponse
    fun activate(id: UUID): ClientActiveStatusResponse
    fun deactivate(id: UUID): ClientActiveStatusResponse
}