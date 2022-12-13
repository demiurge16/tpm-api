package net.nuclearprometheus.translationprojectmanager.adapters.database.client.mappers

import net.nuclearprometheus.translationprojectmanager.adapters.database.client.entities.ClientDatabaseModel
import net.nuclearprometheus.translationprojectmanager.domain.model.client.Client
import net.nuclearprometheus.translationprojectmanager.domain.model.client.ClientId
import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.CountryCode
import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.UnknownCountry
import net.nuclearprometheus.translationprojectmanager.domain.ports.repositories.dictionaries.CountryRepository

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
