package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientView
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.Page
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.Client
import java.util.UUID

interface ClientViewRequestHandler {

    fun getClients(query: FilteredRequest<Client>): Page<ClientView>
    fun getClient(id: UUID): ClientView

}