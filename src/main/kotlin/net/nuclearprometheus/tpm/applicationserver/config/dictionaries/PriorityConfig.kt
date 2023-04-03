package net.nuclearprometheus.tpm.applicationserver.config.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.PriorityRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.PriorityService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.PriorityServiceImpl
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PriorityConfig(
    private val priorityRepository: PriorityRepository
) {

    private val logger = loggerFor(PriorityService::class.java)

    @Bean
    fun priorityService(): PriorityService = PriorityServiceImpl(priorityRepository, logger)
}
