package net.nuclearprometheus.tpm.applicationserver.config.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.AccuracyRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.AccuracyService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.AccuracyServiceImpl
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AccuracyConfig(
    private val accuracyRepository: AccuracyRepository
) {

    private val logger = loggerFor(AccuracyService::class.java)

    @Bean
    fun accuracyService(): AccuracyService = AccuracyServiceImpl(accuracyRepository, logger)
}
