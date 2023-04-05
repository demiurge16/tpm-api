package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers.toCreateResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.ClientTypeCreateRequest
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client.ClientTypeService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Service

@Service
class ClientTypeCreateRequestHandler(private val service: ClientTypeService) {

    private val logger = loggerFor(this::class.java)

    @CacheEvict(value = ["client-types-cache"], allEntries = true)
    fun createClientType(request: ClientTypeCreateRequest) =
        with(logger) {
            info("ClientType create request handler, method createClientType")
            info("Request: $request")

            service.create(request.name, request.description, request.corporate).toCreateResponse()
        }
}
