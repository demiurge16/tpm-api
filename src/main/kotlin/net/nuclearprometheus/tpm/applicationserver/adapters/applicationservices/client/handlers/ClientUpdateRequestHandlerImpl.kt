package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers.toUpdateResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.ClientUpdateRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientId
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientTypeId
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CountryCode
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client.ClientService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientUpdateRequestHandlerImpl(private val service: ClientService) : ClientUpdateRequestHandler {

    private val logger = loggerFor(this::class.java)

    @CacheEvict("clients-cache", allEntries = true)
    override fun update(id: UUID, request: ClientUpdateRequest) =
        with(logger) {
            info("Client update request handler, method update")
            info("Request: $request")

            service.update(
                ClientId(id),
                request.name,
                request.email,
                request.phone,
                request.address,
                request.city,
                request.state,
                request.zip,
                CountryCode(request.countryCode),
                request.vat,
                request.notes,
                ClientTypeId(request.clientTypeId)
            ).toUpdateResponse()
        }
}