package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientType
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.Country
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.Client

object ClientMapper {

    fun Client.toView() = ClientResponse.Client(
        id = id.value,
        name = name,
        email = email,
        phone = phone,
        address = address,
        city = city,
        state = state,
        zip = zip,
        country = Country(
            code = country.id.value,
            name = country.name.official
        ),
        vat = vat,
        notes = notes,
        type = ClientType(
            id = type.id.value,
            name = type.name,
            description = type.description,
            corporate = type.corporate
        ),
        active = active
    )

    fun Client.toActivityStatus() = ClientResponse.Status(
        id = id.value,
        active = active
    )
}