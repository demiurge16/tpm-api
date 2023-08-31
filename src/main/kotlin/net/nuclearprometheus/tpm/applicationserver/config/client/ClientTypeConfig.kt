package net.nuclearprometheus.tpm.applicationserver.config.client

import net.nuclearprometheus.tpm.applicationserver.config.security.PolicyEnforcerPathsProvider
import net.nuclearprometheus.tpm.applicationserver.config.security.methodConfig
import net.nuclearprometheus.tpm.applicationserver.config.security.pathConfig
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientTypeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client.ClientTypeService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client.ClientTypeServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClientTypeConfig(private val clientTypeRepository: ClientTypeRepository) {

    @Bean
    fun clientTypeService(): ClientTypeService = ClientTypeServiceImpl(clientTypeRepository)

    @Bean
    fun clientTypePolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/client-type"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:client-type:read")
                    },
                    methodConfig {
                        method = "POST"
                        scopes = mutableListOf("tpm-backend:client-type:write")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/client-type/{id}"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:client-type:read")
                    },
                    methodConfig {
                        method = "PUT"
                        scopes = mutableListOf("tpm-backend:client-type:write")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/client-type/{id}/*"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:client-type:write")
                    }
                )
            }
        )
    }
}
