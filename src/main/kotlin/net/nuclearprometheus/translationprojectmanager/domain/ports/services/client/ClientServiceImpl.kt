package net.nuclearprometheus.translationprojectmanager.domain.ports.services.client

import net.nuclearprometheus.translationprojectmanager.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.translationprojectmanager.domain.model.client.Client
import net.nuclearprometheus.translationprojectmanager.domain.model.client.ClientId
import net.nuclearprometheus.translationprojectmanager.domain.model.client.ClientTypeId
import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.CountryCode
import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.UnknownCountry
import net.nuclearprometheus.translationprojectmanager.domain.ports.repositories.client.ClientRepository
import net.nuclearprometheus.translationprojectmanager.domain.ports.repositories.client.ClientTypeRepository
import net.nuclearprometheus.translationprojectmanager.domain.ports.repositories.dictionaries.CountryRepository
import java.util.*

class ClientServiceImpl(
    private val repository: ClientRepository,
    private val clientTypeRepository: ClientTypeRepository,
    private val countryRespository: CountryRepository
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
        val client = repository.get(id.value) ?: throw NotFoundException("Client not found")
        val clientType = clientTypeRepository.get(clientTypeId) ?: throw NotFoundException("Client type not found")
        val country = countryRespository.getByCode(countryCode.value) ?: UnknownCountry(countryCode)

        client.update(name, email, phone, address, city, state, zip, country, vat, notes, clientType)

        return repository.update(client)
    }

    override fun activate(id: ClientId): Client {
        val client = repository.get(id.value) ?: throw NotFoundException("Client not found")
        client.activate()

        return repository.update(client)
    }

    override fun deactivate(id: ClientId): Client {
        val client = repository.get(id.value) ?: throw NotFoundException("Client not found")
        client.deactivate()

        return repository.update(client)
    }
}