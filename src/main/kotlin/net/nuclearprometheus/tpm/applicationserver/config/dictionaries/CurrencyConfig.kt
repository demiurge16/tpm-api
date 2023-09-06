package net.nuclearprometheus.tpm.applicationserver.config.dictionaries

import net.nuclearprometheus.tpm.applicationserver.config.security.PolicyEnforcerPathsProvider
import net.nuclearprometheus.tpm.applicationserver.config.security.methodConfig
import net.nuclearprometheus.tpm.applicationserver.config.security.pathConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CurrencyConfig {

    @Bean
    fun currencyPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/currency"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:currency:query")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/currency/export"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:currency:export")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/currency/{code}"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:currency:read")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/currency/{code}/exchange/{amount}"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:currency:read")
                    }
                )
            }
        )
    }
}