package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client

import net.nuclearprometheus.tpm.applicationserver.domain.model.client.Client
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientId
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientTypeId
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CountryCode

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
