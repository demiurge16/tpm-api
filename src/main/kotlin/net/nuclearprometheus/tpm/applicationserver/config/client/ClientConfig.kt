package net.nuclearprometheus.tpm.applicationserver.config.client

import net.nuclearprometheus.tpm.applicationserver.config.security.PolicyEnforcerPathsProvider
import net.nuclearprometheus.tpm.applicationserver.config.security.methodConfig
import net.nuclearprometheus.tpm.applicationserver.config.security.pathConfig
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientTypeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.CountryRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client.ClientService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client.ClientServiceImpl
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
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
    fun clientPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/client"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:client:read")
                    },
                    methodConfig {
                        method = "POST"
                        scopes = mutableListOf("tpm-backend:client:write")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/client/{clientId}"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:client:read")
                    },
                    methodConfig {
                        method = "PUT"
                        scopes = mutableListOf("tpm-backend:client:write")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/client/{clientId}/*"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:client:write")
                    }
                )
            }
        )
    }
}
