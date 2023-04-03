package net.nuclearprometheus.tpm.applicationserver.config.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.IndustryRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.IndustryService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.IndustryServiceImpl
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class IndustryConfig(
    private val industryRepository: IndustryRepository
) {

    private val logger = loggerFor(IndustryService::class.java)

    @Bean
    fun industryService(): IndustryService = IndustryServiceImpl(industryRepository, logger)
}
