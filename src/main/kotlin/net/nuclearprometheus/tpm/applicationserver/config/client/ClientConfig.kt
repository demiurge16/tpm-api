package net.nuclearprometheus.tpm.applicationserver.config.client

import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientTypeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.CountryRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client.ClientService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client.ClientServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClientConfig(
    private val clientRepository: ClientRepository,
    private val clientTypeRepository: ClientTypeRepository,
    private val countryRepository: CountryRepository
) {
    private val logger = loggerFor(ClientService::class.java)

    @Bean fun clientService(): ClientService = ClientServiceImpl(clientRepository, clientTypeRepository, countryRepository, logger)
}
