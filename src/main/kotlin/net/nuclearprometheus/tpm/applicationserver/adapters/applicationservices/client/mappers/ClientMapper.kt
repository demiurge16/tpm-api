package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.Client

object ClientMapper {

    fun Client.toView() = ClientResponse.View(
        id = id.value,
        name = name,
        email = email,
        phone = phone,
        address = address,
        city = city,
        state = state,
        zip = zip,
        country = ClientResponse.View.CountryView(
            code = country.id.value,
            name = country.name
        ),
        vat = vat,
        notes = notes,
        type = ClientResponse.View.ClientTypeView(
            id = type.id.value,
            name = type.name,
            description = type.description,
            corporate = type.corporate
        ),
        active = active
    )

    fun Client.toActivityStatus() = ClientResponse.ActivityStatus(
        id = id.value,
        active = active
    )
}