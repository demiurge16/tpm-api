package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers.toCreateResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.ClientCreateRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientTypeId
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CountryCode
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client.ClientService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Service

@Service
class ClientCreateRequestHandler(private val service: ClientService) {

    private val logger = loggerFor(this::class.java)

    @CacheEvict("clients-cache", allEntries = true)
    fun create(request: ClientCreateRequest) =
        with(logger) {
            info("Client create request handler, method create")
            info("Request: $request")

            service.create(
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
            ).toCreateResponse()
        }


}