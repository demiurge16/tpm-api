package net.nuclearprometheus.tpm.applicationserver.config.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.ExpenseCategoryRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.ExpenseCategoryService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.ExpenseCategoryServiceImpl
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ExpenseCategoryConfig(
    private val expenseCategoryRepository: ExpenseCategoryRepository
) {

    private val logger = loggerFor(ExpenseCategoryService::class.java)

    @Bean
    fun expenseCategoryService(): ExpenseCategoryService = ExpenseCategoryServiceImpl(expenseCategoryRepository, logger)
}