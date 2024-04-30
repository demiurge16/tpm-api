package net.nuclearprometheus.tpm.applicationserver.config.client

import net.nuclearprometheus.tpm.applicationserver.config.security.PolicyEnforcerPathsProvider
import net.nuclearprometheus.tpm.applicationserver.config.security.methodConfig
import net.nuclearprometheus.tpm.applicationserver.config.security.pathConfig
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.specification.ClientTypeSpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientTypeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client.ClientTypeService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client.ClientTypeServiceImpl
import org.keycloak.representations.adapters.config.PolicyEnforcerConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClientTypeConfig(private val clientTypeRepository: ClientTypeRepository) {

    @Bean
    fun clientTypeService(): ClientTypeService = ClientTypeServiceImpl(clientTypeRepository)

    @Bean
    fun clientTypeSpecificationBuilder() = ClientTypeSpecificationBuilder

    @Bean
    fun clientTypePolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/client-type"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:client-type:query", "urn:tpm-backend:resource:client-type:read")
                        scopesEnforcementMode = PolicyEnforcerConfig.ScopeEnforcementMode.ANY
                    },
                    methodConfig {
                        method = "POST"
                        scopes = mutableListOf("urn:tpm-backend:resource:client-type:create")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/client-type/export"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:client-type:export")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/client-type/{clientTypeId}"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:client-type:read")
                    },
                    methodConfig {
                        method = "PUT"
                        scopes = mutableListOf("urn:tpm-backend:resource:client-type:update")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/client-type/{clientTypeId}/*"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:client-type:update")
                    }
                )
            }
        )
    }
}
