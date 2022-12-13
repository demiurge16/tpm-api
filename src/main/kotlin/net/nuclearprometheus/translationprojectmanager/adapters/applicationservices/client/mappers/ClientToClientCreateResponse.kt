package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.mappers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.responses.ClientCreateResponse
import net.nuclearprometheus.translationprojectmanager.domain.model.client.Client

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
