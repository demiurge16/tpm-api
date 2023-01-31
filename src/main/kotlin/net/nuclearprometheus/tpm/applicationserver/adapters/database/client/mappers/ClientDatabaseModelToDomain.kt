package net.nuclearprometheus.tpm.applicationserver.adapters.database.client.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.database.client.entities.ClientDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.Client
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientId
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CountryCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.UnknownCountry
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.CountryRepository

fun ClientDatabaseModel.toDomain(countryRepository: CountryRepository) = Client(
    id = ClientId(id),
    name = name,
    email = email,
    phone = phone,
    address = address,
    city = city,
    state = state,
    zip = zip,
    country = countryRepository.getByCode(countryCode) ?: UnknownCountry(CountryCode(countryCode)),
    vat = vat,
    notes = notes,
    type = type.toDomain(),
    active = active
)
