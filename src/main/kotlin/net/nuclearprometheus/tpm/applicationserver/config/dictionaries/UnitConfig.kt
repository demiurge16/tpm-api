package net.nuclearprometheus.tpm.applicationserver.config.dictionaries

import net.nuclearprometheus.tpm.applicationserver.config.security.PolicyEnforcerPathsProvider
import net.nuclearprometheus.tpm.applicationserver.config.security.methodConfig
import net.nuclearprometheus.tpm.applicationserver.config.security.pathConfig
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.UnitRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.UnitService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.UnitServiceImpl
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.specification.UnitSpecificationBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UnitConfig(private val unitRepository: UnitRepository) {

    @Bean
    fun unitService(): UnitService = UnitServiceImpl(unitRepository, loggerFor(UnitService::class.java))

    @Bean
    fun unitSpecificationBuilder() = UnitSpecificationBuilder

    @Bean
    fun unitPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/unit"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:unit:query")
                    },
                    methodConfig {
                        method = "POST"
                        scopes = mutableListOf("urn:tpm-backend:resource:unit:create")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/unit/export"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:unit:export")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/unit/{unitId}"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:unit:read")
                    },
                    methodConfig {
                        method = "PUT"
                        scopes = mutableListOf("urn:tpm-backend:resource:unit:update")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/unit/{unitId}/*"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:unit:update")
                    }
                )
            }
        )
    }
}
