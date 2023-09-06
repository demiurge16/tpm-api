package net.nuclearprometheus.tpm.applicationserver.config.dictionaries

import net.nuclearprometheus.tpm.applicationserver.config.security.PolicyEnforcerPathsProvider
import net.nuclearprometheus.tpm.applicationserver.config.security.methodConfig
import net.nuclearprometheus.tpm.applicationserver.config.security.pathConfig
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.PriorityRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.PriorityService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.PriorityServiceImpl
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PriorityConfig(private val priorityRepository: PriorityRepository) {

    @Bean
    fun priorityService(): PriorityService = PriorityServiceImpl(
        priorityRepository,
        loggerFor(PriorityService::class.java)
    )

    @Bean
    fun priorityPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/priority"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:priority:query")
                    },
                    methodConfig {
                        method = "POST"
                        scopes = mutableListOf("urn:tpm-backend:resource:priority:create")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/priority/export"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:priority:export")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/priority/{priorityId}"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:priority:read")
                    },
                    methodConfig {
                        method = "PUT"
                        scopes = mutableListOf("urn:tpm-backend:resource:priority:update")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/priority/{priorityId}/*"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:priority:update")
                    }
                )
            }
        )
    }
}
