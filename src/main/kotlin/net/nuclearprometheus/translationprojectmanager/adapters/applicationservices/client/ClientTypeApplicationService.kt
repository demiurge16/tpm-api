package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.requests.*
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.responses.*
import java.util.*

interface ClientTypeApplicationService {
    fun getClientTypes(): List<ClientTypeView>
    fun getClientType(id: UUID): ClientTypeView
    fun createClientType(request: ClientTypeCreateRequest): ClientTypeCreateResponse
    fun updateClientType(id: UUID, request: ClientTypeUpdateRequest): ClientTypeUpdateResponse
    fun activate(id: UUID): ClientTypeActiveStatusResponse
    fun deactivate(id: UUID): ClientTypeActiveStatusResponse
}
