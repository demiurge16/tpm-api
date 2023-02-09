package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers.toUpdateResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.ClientTypeUpdateRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientTypeId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client.ClientTypeService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientTypeUpdateRequestHandlerImpl(
    private val service: ClientTypeService
) : ClientTypeUpdateRequestHandler {

    private val logger = loggerFor(this::class.java)

    @CacheEvict(value = ["client-types-cache"], allEntries = true)
    override fun updateClientType(id: UUID, request: ClientTypeUpdateRequest) =
        with(logger) {
            info("ClientType update request handler, method updateClientType")
            info("id: $id, request: $request")

            service.update(ClientTypeId(id), request.name, request.description, request.corporate).toUpdateResponse()
        }
}
