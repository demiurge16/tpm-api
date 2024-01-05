package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.Client
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientId
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientType
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientTypeId
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CountryCode
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientTypeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.CountryRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.logging.Logger
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.kotlin.*

class ClientServiceImplTest {

    @Test
    fun `create() should create a new client`() {
        // Arrange
        val clientTypeId = ClientTypeId()
        val clientType = ClientType(
            clientTypeId,
            name = "Test Type",
            description = "Test Description",
            corporate = true,
            active = true
        )
        val clientTypeRepository = mock<ClientTypeRepository> {
            on { get(clientTypeId) } doReturn clientType
        }

        val country = Country(
            cca3 = CountryCode(value = "USA"),
            cca2 = "US",
            ccn3 = "840",
            name = Country.Name("United States", "United States", mapOf()),
            topLevelDomains = listOf(),
            currencies = mapOf(),
            internationalDirectDialing = Country.InternationalDirectDialing("", listOf()),
            capital = listOf(),
            altSpellings = listOf(),
            languages = listOf(),
            translations = mapOf(),
            flag = "🇺🇸",
            postalCode = Country.PostalCodeInfo("", "")
        )
        val countryRepository = mock<CountryRepository> {
            on { getByCode("USA") } doReturn country
        }

        val clientId = ClientId()
        val client = Client(
            id = clientId,
            name = "Test Name",
            email = "test@test.com",
            phone = "1234567890",
            address = "Test Address",
            city = "Test City",
            state = "Test State",
            zip = "12345",
            country = country,
            vat = "123456789",
            notes = "Test Notes",
            type = clientType
        )
        val clientRepository = mock<ClientRepository> {
            on { create(any()) } doReturn client
        }

        val logger = mock<Logger> {
            on { trace(any()) } doAnswer { println(it.arguments[0]) }
            on { debug(any()) } doAnswer { println(it.arguments[0]) }
            on { info(any()) } doAnswer { println(it.arguments[0]) }
            on { warn(any()) } doAnswer { println(it.arguments[0]) }
            on { error(any()) } doAnswer { println(it.arguments[0]) }
        }

        val clientService = ClientServiceImpl(clientRepository, clientTypeRepository, countryRepository, logger)

        // Act
        val createdClient = clientService.create(
            name = "Test Name",
            email = "test@test.com",
            phone = "1234567890",
            address = "Test Address",
            city = "Test City",
            state = "Test State",
            zip = "12345",
            countryCode = CountryCode("USA"),
            vat = "123456789",
            notes = "Test Notes",
            clientTypeId = clientTypeId
        )

        // Assert
        assertEquals(client, createdClient)
    }

    @Test
    fun `create() should throw NotFoundException when client type is not found`() {
        // Arrange
        val clientTypeId = ClientTypeId()
        val clientTypeRepository = mock<ClientTypeRepository> {
            on { get(clientTypeId) } doReturn null
        }

        val countryRepository = mock<CountryRepository>()
        val clientRepository = mock<ClientRepository>()
        val logger = mock<Logger>()

        val clientService = ClientServiceImpl(clientRepository, clientTypeRepository, countryRepository, logger)

        // Act
        val exception = assertThrows(NotFoundException::class.java) {
            clientService.create(
                name = "Test Name",
                email = "test@test.com",
                phone = "1234567890",
                address = "Test Address",
                city = "Test City",
                state = "Test State",
                zip = "12345",
                countryCode = CountryCode("USA"),
                vat = "123456789",
                notes = "Test Notes",
                clientTypeId = clientTypeId
            )
        }

        // Assert
        assertEquals("Client type not found", exception.message)
    }

    @Test
    fun `create() should use UnknownCountry with code when country is not found`() {
        // Arrange
        val clientTypeId = ClientTypeId()
        val clientType = ClientType(
            clientTypeId,
            name = "Test Type",
            description = "Test Description",
            corporate = true,
            active = true
        )
        val clientTypeRepository = mock<ClientTypeRepository> {
            on { get(clientTypeId) } doReturn clientType
        }

        val countryRepository = mock<CountryRepository> {
            on { getByCode("USA") } doReturn null
        }

        val clientId = ClientId()
        val client = Client(
            id = clientId,
            name = "Test Name",
            email = "test@test.com",
            phone = "1234567890",
            address = "Test Address",
            city = "Test City",
            state = "Test State",
            zip = "12345",
            country = Country.UnknownCountry(CountryCode("USA")),
            vat = "123456789",
            notes = "Test Notes",
            type = clientType
        )
        val clientRepository = mock<ClientRepository> {
            on { create(any()) } doReturn client
        }

        val logger = mock<Logger> {
            on { trace(any()) } doAnswer { println(it.arguments[0]) }
            on { debug(any()) } doAnswer { println(it.arguments[0]) }
            on { info(any()) } doAnswer { println(it.arguments[0]) }
            on { warn(any()) } doAnswer { println(it.arguments[0]) }
            on { error(any()) } doAnswer { println(it.arguments[0]) }
        }

        val clientService = ClientServiceImpl(clientRepository, clientTypeRepository, countryRepository, logger)

        // Act
        val createdClient = clientService.create(
            name = "Test Name",
            email = "test@test.com",
            phone = "1234567890",
            address = "Test Address",
            city = "Test City",
            state = "Test State",
            zip = "12345",
            countryCode = CountryCode("USA"),
            vat = "123456789",
            notes = "Test Notes",
            clientTypeId = clientTypeId
        )

        // Assert
        assertEquals(client, createdClient)
    }

    @Test
    fun `update() should update a client`() {
        // Arrange
        val clientTypeId = ClientTypeId()
        val clientType = ClientType(
            clientTypeId,
            name = "Test Type",
            description = "Test Description",
            corporate = true,
            active = true
        )

        val clientTypeRepository = mock<ClientTypeRepository> {
            on { get(clientTypeId) } doReturn clientType
        }

        val country = Country(
            cca3 = CountryCode(value = "USA"),
            cca2 = "US",
            ccn3 = "840",
            name = Country.Name("United States", "United States", mapOf()),
            topLevelDomains = listOf(),
            currencies = mapOf(),
            internationalDirectDialing = Country.InternationalDirectDialing("", listOf()),
            capital = listOf(),
            altSpellings = listOf(),
            languages = listOf(),
            translations = mapOf(),
            flag = "🇺🇸",
            postalCode = Country.PostalCodeInfo("", "")
        )
        val countryRepository = mock<CountryRepository> {
            on { getByCode("USA") } doReturn country
        }

        val clientId = ClientId()
        val client = Client(
            id = clientId,
            name = "Test Name",
            email = "test@test.com",
            phone = "1234567890",
            address = "Test Address",
            city = "Test City",
            state = "Test State",
            zip = "12345",
            country = country,
            vat = "123456789",
            notes = "Test Notes",
            type = clientType
        )
        val clientAfterUpdate = Client(
            id = clientId,
            name = "Updated Name",
            email = "test@test.com",
            phone = "1234567890",
            address = "Test Address",
            city = "Test City",
            state = "Test State",
            zip = "12345",
            country = country,
            vat = "123456789",
            notes = "Test Notes",
            type = clientType
        )
        val clientRepository = mock<ClientRepository> {
            on { get(clientId) } doReturn client
            on { update(any()) } doReturn clientAfterUpdate
        }

        val logger = mock<Logger> {
            on { trace(any()) } doAnswer { println(it.arguments[0]) }
            on { debug(any()) } doAnswer { println(it.arguments[0]) }
            on { info(any()) } doAnswer { println(it.arguments[0]) }
            on { warn(any()) } doAnswer { println(it.arguments[0]) }
            on { error(any()) } doAnswer { println(it.arguments[0]) }
        }

        val clientService = ClientServiceImpl(clientRepository, clientTypeRepository, countryRepository, logger)

        // Act
        val updatedClient = clientService.update(
            id = clientId,
            name = "Updated Name",
            email = "test@test.com",
            phone = "1234567890",
            address = "Test Address",
            city = "Test City",
            state = "Test State",
            zip = "12345",
            countryCode = CountryCode("USA"),
            vat = "123456789",
            notes = "Test Notes",
            clientTypeId = clientTypeId
        )

        // Assert
        assertEquals(clientAfterUpdate, updatedClient)
    }

    @Test
    fun `update() should throw NotFoundException when client is not found`() {
        // Arrange
        val clientTypeId = ClientTypeId()
        val clientType = ClientType(
            clientTypeId,
            name = "Test Type",
            description = "Test Description",
            corporate = true,
            active = true
        )

        val clientTypeRepository = mock<ClientTypeRepository> {
            on { get(clientTypeId) } doReturn clientType
        }

        val country = Country(
            cca3 = CountryCode(value = "USA"),
            cca2 = "US",
            ccn3 = "840",
            name = Country.Name("United States", "United States", mapOf()),
            topLevelDomains = listOf(),
            currencies = mapOf(),
            internationalDirectDialing = Country.InternationalDirectDialing("", listOf()),
            capital = listOf(),
            altSpellings = listOf(),
            languages = listOf(),
            translations = mapOf(),
            flag = "🇺🇸",
            postalCode = Country.PostalCodeInfo("", "")
        )
        val countryRepository = mock<CountryRepository> {
            on { getByCode("USA") } doReturn country
        }

        val clientId = ClientId()
        val clientRepository = mock<ClientRepository> {
            on { get(clientId) } doReturn null
        }

        val logger = mock<Logger> {
            on { trace(any()) } doAnswer { println(it.arguments[0]) }
            on { debug(any()) } doAnswer { println(it.arguments[0]) }
            on { info(any()) } doAnswer { println(it.arguments[0]) }
            on { warn(any()) } doAnswer { println(it.arguments[0]) }
            on { error(any()) } doAnswer { println(it.arguments[0]) }
        }

        val clientService = ClientServiceImpl(clientRepository, clientTypeRepository, countryRepository, logger)

        // Act
        val exception = assertThrows(NotFoundException::class.java) {
            clientService.update(
                id = clientId,
                name = "Updated Name",
                email = "test@test.com",
                phone = "1234567890",
                address = "Test Address",
                city = "Test City",
                state = "Test State",
                zip = "12345",
                countryCode = CountryCode("USA"),
                vat = "123456789",
                notes = "Test Notes",
                clientTypeId = clientTypeId
            )
        }

        // Assert
        assertEquals("Client not found", exception.message)
    }

    @Test
    fun `update() should throw NotFoundException when client type is not found`() {
        // Arrange
        val clientTypeId = ClientTypeId()
        val clientType = ClientType(
            clientTypeId,
            name = "Test Type",
            description = "Test Description",
            corporate = true,
            active = true
        )

        val clientTypeRepository = mock<ClientTypeRepository> {
            on { get(clientTypeId) } doReturn null
        }

        val country = Country(
            cca3 = CountryCode(value = "USA"),
            cca2 = "US",
            ccn3 = "840",
            name = Country.Name("United States", "United States", mapOf()),
            topLevelDomains = listOf(),
            currencies = mapOf(),
            internationalDirectDialing = Country.InternationalDirectDialing("", listOf()),
            capital = listOf(),
            altSpellings = listOf(),
            languages = listOf(),
            translations = mapOf(),
            flag = "🇺🇸",
            postalCode = Country.PostalCodeInfo("", "")
        )
        val countryRepository = mock<CountryRepository> {
            on { getByCode("USA") } doReturn country
        }

        val clientId = ClientId()
        val client = Client(
            id = clientId,
            name = "Test Name",
            email = "test@test.com",
            phone = "1234567890",
            address = "Test Address",
            city = "Test City",
            state = "Test State",
            zip = "12345",
            country = country,
            vat = "123456789",
            notes = "Test Notes",
            type = clientType
        )
        val clientAfterUpdate = Client(
            id = clientId,
            name = "Updated Name",
            email = "test@test.com",
            phone = "1234567890",
            address = "Test Address",
            city = "Test City",
            state = "Test State",
            zip = "12345",
            country = country,
            vat = "123456789",
            notes = "Test Notes",
            type = clientType
        )
        val clientRepository = mock<ClientRepository> {
            on { get(clientId) } doReturn client
            on { update(any()) } doReturn clientAfterUpdate
        }

        val logger = mock<Logger> {
            on { trace(any()) } doAnswer { println(it.arguments[0]) }
            on { debug(any()) } doAnswer { println(it.arguments[0]) }
            on { info(any()) } doAnswer { println(it.arguments[0]) }
            on { warn(any()) } doAnswer { println(it.arguments[0]) }
            on { error(any()) } doAnswer { println(it.arguments[0]) }
        }

        val clientService = ClientServiceImpl(clientRepository, clientTypeRepository, countryRepository, logger)

        // Act
        val exception = assertThrows(NotFoundException::class.java) {
            clientService.update(
                id = clientId,
                name = "Updated Name",
                email = "test@test.com",
                phone = "1234567890",
                address = "Test Address",
                city = "Test City",
                state = "Test State",
                zip = "12345",
                countryCode = CountryCode("USA"),
                vat = "123456789",
                notes = "Test Notes",
                clientTypeId = clientTypeId
            )
        }

        // Assert
        assertEquals("Client type not found", exception.message)
    }

    @Test
    fun `update() should use UnknownCountry with code when country is not found`() {
        // Arrange
        val clientTypeId = ClientTypeId()
        val clientType = ClientType(
            clientTypeId,
            name = "Test Type",
            description = "Test Description",
            corporate = true,
            active = true
        )

        val clientTypeRepository = mock<ClientTypeRepository> {
            on { get(clientTypeId) } doReturn clientType
        }

        val country = Country(
            cca3 = CountryCode(value = "USA"),
            cca2 = "US",
            ccn3 = "840",
            name = Country.Name("United States", "United States", mapOf()),
            topLevelDomains = listOf(),
            currencies = mapOf(),
            internationalDirectDialing = Country.InternationalDirectDialing("", listOf()),
            capital = listOf(),
            altSpellings = listOf(),
            languages = listOf(),
            translations = mapOf(),
            flag = "🇺🇸",
            postalCode = Country.PostalCodeInfo("", "")
        )
        val countryRepository = mock<CountryRepository> {
            on { getByCode("USA") } doReturn country
            on { getByCode("ZZZ") } doReturn null
        }

        val clientId = ClientId()
        val client = Client(
            id = clientId,
            name = "Test Name",
            email = "test@test.com",
            phone = "1234567890",
            address = "Test Address",
            city = "Test City",
            state = "Test State",
            zip = "12345",
            country = country,
            vat = "123456789",
            notes = "Test Notes",
            type = clientType
        )
        val clientAfterUpdate = Client(
            id = clientId,
            name = "Updated Name",
            email = "test@test.com",
            phone = "1234567890",
            address = "Test Address",
            city = "Test City",
            state = "Test State",
            zip = "12345",
            country = Country.UnknownCountry(CountryCode("ZZZ")),
            vat = "123456789",
            notes = "Test Notes",
            type = clientType
        )
        val clientRepository = mock<ClientRepository> {
            on { get(clientId) } doReturn client
            on { update(any()) } doReturn clientAfterUpdate
        }

        val logger = mock<Logger> {
            on { trace(any()) } doAnswer { println(it.arguments[0]) }
            on { debug(any()) } doAnswer { println(it.arguments[0]) }
            on { info(any()) } doAnswer { println(it.arguments[0]) }
            on { warn(any()) } doAnswer { println(it.arguments[0]) }
            on { error(any()) } doAnswer { println(it.arguments[0]) }
        }

        val clientService = ClientServiceImpl(clientRepository, clientTypeRepository, countryRepository, logger)

        // Act
        val updatedClient = clientService.update(
            id = clientId,
            name = "Updated Name",
            email = "test@test.com",
            phone = "1234567890",
            address = "Test Address",
            city = "Test City",
            state = "Test State",
            zip = "12345",
            countryCode = CountryCode("ZZZ"),
            vat = "123456789",
            notes = "Test Notes",
            clientTypeId = clientTypeId
        )

        // Assert
        assertEquals(clientAfterUpdate, updatedClient)
    }

    @Test
    fun `activate() should activate a client`() {
        // Arrange
        val clientTypeId = ClientTypeId()
        val clientType = ClientType(
            clientTypeId,
            name = "Test Type",
            description = "Test Description",
            corporate = true,
            active = true
        )

        val clientTypeRepository = mock<ClientTypeRepository> {
            on { get(clientTypeId) } doReturn clientType
        }

        val country = Country(
            cca3 = CountryCode(value = "USA"),
            cca2 = "US",
            ccn3 = "840",
            name = Country.Name("United States", "United States", mapOf()),
            topLevelDomains = listOf(),
            currencies = mapOf(),
            internationalDirectDialing = Country.InternationalDirectDialing("", listOf()),
            capital = listOf(),
            altSpellings = listOf(),
            languages = listOf(),
            translations = mapOf(),
            flag = "🇺🇸",
            postalCode = Country.PostalCodeInfo("", "")
        )
        val countryRepository = mock<CountryRepository> {
            on { getByCode("USA") } doReturn country
        }

        val clientId = ClientId()
        val client = Client(
            id = clientId,
            name = "Test Name",
            email = "test@test.com",
            phone = "1234567890",
            address = "Test Address",
            city = "Test City",
            state = "Test State",
            zip = "12345",
            country = country,
            vat = "123456789",
            notes = "Test Notes",
            type = clientType,
            active = false
        )
        val clientAfterActivation = Client(
            id = clientId,
            name = "Test Name",
            email = "test@test.com",
            phone = "1234567890",
            address = "Test Address",
            city = "Test City",
            state = "Test State",
            zip = "12345",
            country = country,
            vat = "123456789",
            notes = "Test Notes",
            type = clientType,
            active = true
        )
        val clientRepository = mock<ClientRepository> {
            on { get(clientId) } doReturn client
            on { update(any()) } doReturn clientAfterActivation
        }

        val logger = mock<Logger> {
            on { trace(any()) } doAnswer { println(it.arguments[0]) }
            on { debug(any()) } doAnswer { println(it.arguments[0]) }
            on { info(any()) } doAnswer { println(it.arguments[0]) }
            on { warn(any()) } doAnswer { println(it.arguments[0]) }
            on { error(any()) } doAnswer { println(it.arguments[0]) }
        }

        val clientService = ClientServiceImpl(clientRepository, clientTypeRepository, countryRepository, logger)

        // Act
        val activatedClient = clientService.activate(id = clientId)

        // Assert
        assertEquals(clientAfterActivation, activatedClient)
    }

    @Test
    fun `activate() should throw NotFoundException when client is not found`() {
        // Arrange
        val clientTypeId = ClientTypeId()
        val clientType = ClientType(
            clientTypeId,
            name = "Test Type",
            description = "Test Description",
            corporate = true,
            active = true
        )

        val clientTypeRepository = mock<ClientTypeRepository> {
            on { get(clientTypeId) } doReturn clientType
        }

        val country = Country(
            cca3 = CountryCode(value = "USA"),
            cca2 = "US",
            ccn3 = "840",
            name = Country.Name("United States", "United States", mapOf()),
            topLevelDomains = listOf(),
            currencies = mapOf(),
            internationalDirectDialing = Country.InternationalDirectDialing("", listOf()),
            capital = listOf(),
            altSpellings = listOf(),
            languages = listOf(),
            translations = mapOf(),
            flag = "🇺🇸",
            postalCode = Country.PostalCodeInfo("", ""),
        )
        val countryRepository = mock<CountryRepository> {
            on { getByCode("USA") } doReturn country
        }

        val clientId = ClientId()
        val clientRepository = mock<ClientRepository> {
            on { get(clientId) } doReturn null
        }

        val logger = mock<Logger> {
            on { trace(any()) } doAnswer { println(it.arguments[0]) }
            on { debug(any()) } doAnswer { println(it.arguments[0]) }
            on { info(any()) } doAnswer { println(it.arguments[0]) }
            on { warn(any()) } doAnswer { println(it.arguments[0]) }
            on { error(any()) } doAnswer { println(it.arguments[0]) }
        }

        val clientService = ClientServiceImpl(clientRepository, clientTypeRepository, countryRepository, logger)

        // Act
        val exception = assertThrows(NotFoundException::class.java) {
            clientService.activate(id = clientId)
        }

        // Assert
        assertEquals("Client not found", exception.message)
    }

    @Test
    fun `deactivate() should deactivate a client`() {
// Arrange
        val clientTypeId = ClientTypeId()
        val clientType = ClientType(
            clientTypeId,
            name = "Test Type",
            description = "Test Description",
            corporate = true,
            active = true
        )

        val clientTypeRepository = mock<ClientTypeRepository> {
            on { get(clientTypeId) } doReturn clientType
        }

        val country = Country(
            cca3 = CountryCode(value = "USA"),
            cca2 = "US",
            ccn3 = "840",
            name = Country.Name("United States", "United States", mapOf()),
            topLevelDomains = listOf(),
            currencies = mapOf(),
            internationalDirectDialing = Country.InternationalDirectDialing("", listOf()),
            capital = listOf(),
            altSpellings = listOf(),
            languages = listOf(),
            translations = mapOf(),
            flag = "🇺🇸",
            postalCode = Country.PostalCodeInfo("", "")
        )
        val countryRepository = mock<CountryRepository> {
            on { getByCode("USA") } doReturn country
        }

        val clientId = ClientId()
        val client = Client(
            id = clientId,
            name = "Test Name",
            email = "test@test.com",
            phone = "1234567890",
            address = "Test Address",
            city = "Test City",
            state = "Test State",
            zip = "12345",
            country = country,
            vat = "123456789",
            notes = "Test Notes",
            type = clientType,
            active = true
        )
        val clientAfterActivation = Client(
            id = clientId,
            name = "Test Name",
            email = "test@test.com",
            phone = "1234567890",
            address = "Test Address",
            city = "Test City",
            state = "Test State",
            zip = "12345",
            country = country,
            vat = "123456789",
            notes = "Test Notes",
            type = clientType,
            active = false
        )
        val clientRepository = mock<ClientRepository> {
            on { get(clientId) } doReturn client
            on { update(any()) } doReturn clientAfterActivation
        }

        val logger = mock<Logger> {
            on { trace(any()) } doAnswer { println(it.arguments[0]) }
            on { debug(any()) } doAnswer { println(it.arguments[0]) }
            on { info(any()) } doAnswer { println(it.arguments[0]) }
            on { warn(any()) } doAnswer { println(it.arguments[0]) }
            on { error(any()) } doAnswer { println(it.arguments[0]) }
        }

        val clientService = ClientServiceImpl(clientRepository, clientTypeRepository, countryRepository, logger)

        // Act
        val activatedClient = clientService.activate(id = clientId)

        // Assert
        assertEquals(clientAfterActivation, activatedClient)
    }

    @Test
    fun `deactivate() should throw NotFoundException when client is not found`() {
// Arrange
        val clientTypeId = ClientTypeId()
        val clientType = ClientType(
            clientTypeId,
            name = "Test Type",
            description = "Test Description",
            corporate = true,
            active = true
        )

        val clientTypeRepository = mock<ClientTypeRepository> {
            on { get(clientTypeId) } doReturn clientType
        }

        val country = Country(
            cca3 = CountryCode(value = "USA"),
            cca2 = "US",
            ccn3 = "840",
            name = Country.Name("United States", "United States", mapOf()),
            topLevelDomains = listOf(),
            currencies = mapOf(),
            internationalDirectDialing = Country.InternationalDirectDialing("", listOf()),
            capital = listOf(),
            altSpellings = listOf(),
            languages = listOf(),
            translations = mapOf(),
            flag = "🇺🇸",
            postalCode = Country.PostalCodeInfo("", ""),
        )
        val countryRepository = mock<CountryRepository> {
            on { getByCode("USA") } doReturn country
        }

        val clientId = ClientId()
        val clientRepository = mock<ClientRepository> {
            on { get(clientId) } doReturn null
        }

        val logger = mock<Logger> {
            on { trace(any()) } doAnswer { println(it.arguments[0]) }
            on { debug(any()) } doAnswer { println(it.arguments[0]) }
            on { info(any()) } doAnswer { println(it.arguments[0]) }
            on { warn(any()) } doAnswer { println(it.arguments[0]) }
            on { error(any()) } doAnswer { println(it.arguments[0]) }
        }

        val clientService = ClientServiceImpl(clientRepository, clientTypeRepository, countryRepository, logger)

        // Act
        val exception = assertThrows(NotFoundException::class.java) {
            clientService.deactivate(id = clientId)
        }

        // Assert
        assertEquals("Client not found", exception.message)
    }
}