package net.nuclearprometheus.tpm.applicationserver.config.client

import net.nuclearprometheus.tpm.applicationserver.config.security.PolicyEnforcerPathsProvider
import net.nuclearprometheus.tpm.applicationserver.config.security.methodConfig
import net.nuclearprometheus.tpm.applicationserver.config.security.pathConfig
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientTypeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.CountryRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client.ClientService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client.ClientServiceImpl
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.specification.ClientSpecificationBuilder
import org.keycloak.representations.adapters.config.PolicyEnforcerConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClientConfig(
    private val clientRepository: ClientRepository,
    private val clientTypeRepository: ClientTypeRepository,
    private val countryRepository: CountryRepository
) {

    @Bean
    fun clientService(): ClientService = ClientServiceImpl(
        clientRepository,
        clientTypeRepository,
        countryRepository,
        loggerFor(ClientService::class.java)
    )

    @Bean
    fun clientSpecificationBuilder() = ClientSpecificationBuilder

    @Bean
    fun clientPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/client"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:client:query", "urn:tpm-backend:resource:client:read")
                        scopesEnforcementMode = PolicyEnforcerConfig.ScopeEnforcementMode.ANY
                    },
                    methodConfig {
                        method = "POST"
                        scopes = mutableListOf("urn:tpm-backend:resource:client:create")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/client/export"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:client:export")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/client/{clientId}"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:client:read")
                    },
                    methodConfig {
                        method = "PUT"
                        scopes = mutableListOf("urn:tpm-backend:resource:client:update")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/client/{clientId}/*"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:client:update")
                    }
                )
            }
        )
    }
}
