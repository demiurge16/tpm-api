package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers.toActiveStatusResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientTypeId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client.ClientTypeService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientTypeActiveStatusRequestHandlerImpl(private val service: ClientTypeService) : ClientTypeActiveStatusRequestHandler {

    private val logger = loggerFor(this::class.java)

    @CacheEvict(value = ["client-types"], allEntries = true)
    override fun activate(id: UUID) =
        with(logger) {
            info("ClientType active status request handler, method activate")
            info("id: $id")

            service.activate(ClientTypeId(id)).toActiveStatusResponse()
        }

    @CacheEvict(value = ["client-types"], allEntries = true)
    override fun deactivate(id: UUID) =
        with(logger) {
            info("ClientType active status request handler, method deactivate")
            info("id: $id")

            service.deactivate(ClientTypeId(id)).toActiveStatusResponse()
        }
}