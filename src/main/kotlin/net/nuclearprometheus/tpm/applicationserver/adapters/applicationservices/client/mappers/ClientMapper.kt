package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers.ClientTypeMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientStatus
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.Client as ClientResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.CountryMapper.toView
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.Client

object ClientMapper {

    fun Client.toView() = ClientResponse(
        id = id.value,
        name = name,
        email = email,
        phone = phone,
        address = address,
        city = city,
        state = state,
        zip = zip,
        country = country.toView(),
        vat = vat,
        notes = notes,
        type = type.toView(),
        active = active
    )

    fun Client.toActivityStatus() = ClientStatus(
        id = id.value,
        active = active
    )
}