package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.handlers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.mappers.toUpdateResponse
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.requests.ClientUpdateRequest
import net.nuclearprometheus.translationprojectmanager.domain.model.client.ClientId
import net.nuclearprometheus.translationprojectmanager.domain.model.client.ClientTypeId
import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.CountryCode
import net.nuclearprometheus.translationprojectmanager.domain.ports.services.client.ClientService
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientUpdateRequestHandlerImpl(private val service: ClientService) : ClientUpdateRequestHandler {

    override fun update(id: UUID, request: ClientUpdateRequest) =
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