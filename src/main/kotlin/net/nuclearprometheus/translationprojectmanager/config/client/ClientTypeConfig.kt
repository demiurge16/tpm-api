package net.nuclearprometheus.translationprojectmanager.config.client

import net.nuclearprometheus.translationprojectmanager.domain.ports.repositories.client.ClientTypeRepository
import net.nuclearprometheus.translationprojectmanager.domain.ports.services.client.ClientTypeService
import net.nuclearprometheus.translationprojectmanager.domain.ports.services.client.ClientTypeServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClientTypeConfig(private val clientTypeRepository: ClientTypeRepository) {
    @Bean fun clientTypeService(): ClientTypeService = ClientTypeServiceImpl(clientTypeRepository)
}
