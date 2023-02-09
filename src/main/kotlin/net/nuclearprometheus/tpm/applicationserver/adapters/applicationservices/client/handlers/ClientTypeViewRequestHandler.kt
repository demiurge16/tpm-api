package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientTypeView
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.Page
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientType
import java.util.*

interface ClientTypeViewRequestHandler {
    fun getClientTypes(query: FilteredRequest<ClientType>): Page<ClientTypeView>
    fun getClientType(id: UUID): ClientTypeView
}
