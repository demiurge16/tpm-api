package net.nuclearprometheus.tpm.applicationserver.config.dictionaries

import net.nuclearprometheus.tpm.applicationserver.config.security.PolicyEnforcerPathsProvider
import net.nuclearprometheus.tpm.applicationserver.config.security.methodConfig
import net.nuclearprometheus.tpm.applicationserver.config.security.pathConfig
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.AccuracyRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.AccuracyService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.AccuracyServiceImpl
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AccuracyConfig(private val accuracyRepository: AccuracyRepository) {

    @Bean
    fun accuracyService(): AccuracyService = AccuracyServiceImpl(
        accuracyRepository,
        loggerFor(AccuracyService::class.java)
    )

    @Bean
    fun accuracyPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/accuracy"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:accuracy:read")
                    },
                    methodConfig {
                        method = "POST"
                        scopes = mutableListOf("tpm-backend:accuracy:write")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/accuracy/{accuracyId}"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:accuracy:read")
                    },
                    methodConfig {
                        method = "PUT"
                        scopes = mutableListOf("tpm-backend:accuracy:write")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/accuracy/{accuracyId}/*"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:accuracy:write")
                    }
                )
            }
        )
    }
}
