package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.Client
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientId
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientTypeId
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CountryCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country.UnknownCountry
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientTypeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.CountryRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.logging.Logger

class ClientServiceImpl(
    private val repository: ClientRepository,
    private val clientTypeRepository: ClientTypeRepository,
    private val countryRespository: CountryRepository,
    private val logger: Logger
) : ClientService {

    override fun create(
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
    ): Client {
        val clientType = clientTypeRepository.get(clientTypeId) ?: throw NotFoundException("Client type not found")

        val client = Client(
            name = name,
            email = email,
            phone = phone,
            address = address,
            city = city,
            state = state,
            zip = zip,
            country = countryRespository.getByCode(countryCode.value) ?: UnknownCountry(countryCode),
            vat = vat,
            notes = notes,
            type = clientType
        )

        return repository.create(client)
    }

    override fun update(
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
    ): Client {
        val client = repository.get(id) ?: throw NotFoundException("Client not found")
        val clientType = clientTypeRepository.get(clientTypeId) ?: throw NotFoundException("Client type not found")
        val country = countryRespository.getByCode(countryCode.value) ?: UnknownCountry(countryCode)

        if (country is UnknownCountry) {
            logger.warn("Country with code ${countryCode.value} not found. Using UnknownCountry")
        }

        client.update(name, email, phone, address, city, state, zip, country, vat, notes, clientType)

        return repository.update(client)
    }

    override fun activate(id: ClientId): Client {
        val client = repository.get(id) ?: throw NotFoundException("Client not found")
        client.activate()

        return repository.update(client)
    }

    override fun deactivate(id: ClientId): Client {
        val client = repository.get(id) ?: throw NotFoundException("Client not found")
        client.deactivate()

        return repository.update(client)
    }
}