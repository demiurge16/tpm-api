package net.nuclearprometheus.translationprojectmanager.domain.ports.services.client

import net.nuclearprometheus.translationprojectmanager.domain.model.client.Client
import net.nuclearprometheus.translationprojectmanager.domain.model.client.ClientId
import net.nuclearprometheus.translationprojectmanager.domain.model.client.ClientTypeId
import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.CountryCode
import java.util.*

interface ClientService {
    fun create(
        name: String,
        email: String,
        phone: String,
        address: String,
        city: String,
        state: String,
        zip: String,
        countryCode: CountryCode,
        vat: String,
        notes: String,
        clientTypeId: ClientTypeId
    ): Client

    fun update(
        id: ClientId,
        name: String,
        email: String,
        phone: String,
        address: String,
        city: String,
        state: String,
        zip: String,
        countryCode: CountryCode,
        vat: String,
        notes: String,
        clientTypeId: ClientTypeId
    ): Client

    fun activate(id: ClientId): Client
    fun deactivate(id: ClientId): Client
}
