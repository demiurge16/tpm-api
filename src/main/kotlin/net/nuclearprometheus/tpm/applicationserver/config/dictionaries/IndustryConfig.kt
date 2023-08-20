package net.nuclearprometheus.tpm.applicationserver.config.dictionaries

import net.nuclearprometheus.tpm.applicationserver.config.security.PolicyEnforcerPathsProvider
import net.nuclearprometheus.tpm.applicationserver.config.security.methodConfig
import net.nuclearprometheus.tpm.applicationserver.config.security.pathConfig
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.IndustryRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.IndustryService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.IndustryServiceImpl
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class IndustryConfig(private val industryRepository: IndustryRepository) {

    @Bean
    fun industryService(): IndustryService = IndustryServiceImpl(
        industryRepository,
        loggerFor(IndustryService::class.java)
    )

    @Bean
    fun industryPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/industry"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:industry:read")
                    },
                    methodConfig {
                        method = "POST"
                        scopes = mutableListOf("tpm-backend:industry:write")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/industry/{id}"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:industry:read")
                    },
                    methodConfig {
                        method = "PUT"
                        scopes = mutableListOf("tpm-backend:industry:write")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/industry/{id}/*"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:industry:write")
                    }
                )
            }
        )
    }
}
