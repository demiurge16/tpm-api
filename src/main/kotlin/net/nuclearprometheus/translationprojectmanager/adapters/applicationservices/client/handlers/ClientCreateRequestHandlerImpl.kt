package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.handlers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.mappers.toCreateResponse
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.requests.ClientCreateRequest
import net.nuclearprometheus.translationprojectmanager.domain.model.client.ClientTypeId
import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.CountryCode
import net.nuclearprometheus.translationprojectmanager.domain.ports.services.client.ClientService
import org.springframework.stereotype.Service

@Service
class ClientCreateRequestHandlerImpl(private val service: ClientService) : ClientCreateRequestHandler {

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