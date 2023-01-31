package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers.toCreateResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.ClientCreateRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientTypeId
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CountryCode
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client.ClientService
import org.springframework.stereotype.Service

@Service
class ClientCreateRequestHandlerImpl(private val service: ClientService) :
    net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers.ClientCreateRequestHandler {

    override fun create(request: ClientCreateRequest) =
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