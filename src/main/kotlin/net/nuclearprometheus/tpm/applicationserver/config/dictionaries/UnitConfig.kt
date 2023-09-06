package net.nuclearprometheus.tpm.applicationserver.config.dictionaries

import net.nuclearprometheus.tpm.applicationserver.config.security.PolicyEnforcerPathsProvider
import net.nuclearprometheus.tpm.applicationserver.config.security.methodConfig
import net.nuclearprometheus.tpm.applicationserver.config.security.pathConfig
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.UnitRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.UnitService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.UnitServiceImpl
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UnitConfig(private val unitRepository: UnitRepository) {

    @Bean
    fun unitService(): UnitService = UnitServiceImpl(unitRepository, loggerFor(UnitService::class.java))

    @Bean
    fun unitPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/unit"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:unit:query")
                    },
                    methodConfig {
                        method = "POST"
                        scopes = mutableListOf("tpm-backend:unit:create")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/unit/export"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:unit:export")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/unit/{unitId}"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:unit:read")
                    },
                    methodConfig {
                        method = "PUT"
                        scopes = mutableListOf("tpm-backend:unit:update")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/unit/{unitId}/*"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:unit:update")
                    }
                )
            }
        )
    }
}
