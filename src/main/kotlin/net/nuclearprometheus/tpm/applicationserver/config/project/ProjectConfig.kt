package net.nuclearprometheus.tpm.applicationserver.config.project

import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project.ProjectService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project.ProjectServiceImpl
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProjectConfig(
    private val projectRepository: ProjectRepository,
    private val languageRepository: LanguageRepository,
    private val accuracyRepository: AccuracyRepository,
    private val industryRepository: IndustryRepository,
    private val unitRepository: UnitRepository,
    private val currencyRepository: CurrencyRepository,
    private val clientRepository: ClientRepository
) {

    private val logger = loggerFor(ProjectService::class.java)

    @Bean
    fun projectService(): ProjectService =
        ProjectServiceImpl(
            projectRepository,
            languageRepository,
            accuracyRepository,
            industryRepository,
            unitRepository,
            currencyRepository,
            clientRepository,
            logger
        )
}