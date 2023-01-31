package net.nuclearprometheus.tpm.applicationserver.adapters.database.client.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.database.client.entities.ClientDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.Client

fun Client.toDatabaseModel() = ClientDatabaseModel(
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
    type = type.toDatabaseModel(),
    active = active
)
