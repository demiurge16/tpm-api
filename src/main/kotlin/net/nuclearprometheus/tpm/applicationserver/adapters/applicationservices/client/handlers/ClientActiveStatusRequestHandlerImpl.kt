package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers.toActiveStatusResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client.ClientService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientActiveStatusRequestHandlerImpl(private val service: ClientService) : ClientActiveStatusRequestHandler {

    private val logger = loggerFor(this::class.java)

    @CacheEvict("clients-cache", allEntries = true)
    override fun activate(id: UUID) =
        with(logger) {
            info("Client active status request handler, method activate")
            info("Id: $id")

            service.activate(ClientId(id)).toActiveStatusResponse()
        }

    @CacheEvict("clients-cache", allEntries = true)
    override fun deactivate(id: UUID) =
        with(logger) {
            info("Client active status request handler, method deactivate")
            info("Id: $id")

            service.deactivate(ClientId(id)).toActiveStatusResponse()
        }
}