package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers.toActiveStatusResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientTypeId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client.ClientTypeService
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientTypeActiveStatusRequestHandlerImpl(
    private val service: ClientTypeService
) : net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers.ClientTypeActiveStatusRequestHandler {

    override fun activate(id: UUID) = service.activate(ClientTypeId(id)).toActiveStatusResponse()

    override fun deactivate(id: UUID) = service.deactivate(ClientTypeId(id)).toActiveStatusResponse()
}