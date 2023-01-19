package net.nuclearprometheus.tpm.applicationserver.config.client

import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientTypeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client.ClientTypeService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client.ClientTypeServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClientTypeConfig(private val clientTypeRepository: ClientTypeRepository) {
    @Bean fun clientTypeService(): ClientTypeService = ClientTypeServiceImpl(clientTypeRepository)
}
