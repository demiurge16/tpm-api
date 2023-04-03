package net.nuclearprometheus.tpm.applicationserver.config.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.UnitRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.UnitService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.UnitServiceImpl
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UnitConfig(
    private val unitRepository: UnitRepository
) {

    private val logger = loggerFor(UnitService::class.java)

    @Bean
    fun unitService(): UnitService = UnitServiceImpl(unitRepository, logger)
}
