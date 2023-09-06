package net.nuclearprometheus.tpm.applicationserver.config.dictionaries

import net.nuclearprometheus.tpm.applicationserver.config.security.PolicyEnforcerPathsProvider
import net.nuclearprometheus.tpm.applicationserver.config.security.methodConfig
import net.nuclearprometheus.tpm.applicationserver.config.security.pathConfig
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.ServiceTypeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.ServiceTypeService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.ServiceTypeServiceImpl
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceTypeConfig(private val serviceTypeRepository: ServiceTypeRepository) {

    @Bean
    fun serviceTypeService(): ServiceTypeService = ServiceTypeServiceImpl(
        serviceTypeRepository,
        loggerFor(ServiceTypeService::class.java)
    )

    @Bean
    fun serviceTypePolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/service-type"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:service-type:query")
                    },
                    methodConfig {
                        method = "POST"
                        scopes = mutableListOf("tpm-backend:service-type:create")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/service-type/export"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:service-type:export")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/service-type/{serviceTypeId}"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:service-type:read")
                    },
                    methodConfig {
                        method = "PUT"
                        scopes = mutableListOf("tpm-backend:service-type:update")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/service-type/{serviceTypeId}/*"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:service-type:update")
                    }
                )
            }
        )
    }
}