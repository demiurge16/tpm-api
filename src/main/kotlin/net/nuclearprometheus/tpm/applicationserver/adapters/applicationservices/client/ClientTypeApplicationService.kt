package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.*
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.*
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.Page
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientType
import java.util.*

interface ClientTypeApplicationService {
    fun getClientTypes(query: FilteredRequest<ClientType>): Page<ClientTypeView>
    fun getClientType(id: UUID): ClientTypeView
    fun createClientType(request: ClientTypeCreateRequest): ClientTypeCreateResponse
    fun updateClientType(id: UUID, request: ClientTypeUpdateRequest): ClientTypeUpdateResponse
    fun activate(id: UUID): ClientTypeActiveStatusResponse
    fun deactivate(id: UUID): ClientTypeActiveStatusResponse
}
