package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientCreateResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.Client

fun Client.toCreateResponse() = ClientCreateResponse(
    id = id.value,
    name = name,
    email = email,
    phone = phone,
    address = address,
    city = city,
    state = state,
    zip = zip,
    countryCode = country.code.value,
    vat = vat,
    notes = notes,
    clientTypeId = type.id.value,
    active = active
)
