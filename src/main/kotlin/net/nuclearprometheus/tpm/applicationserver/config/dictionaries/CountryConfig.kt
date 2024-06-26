package net.nuclearprometheus.tpm.applicationserver.config.dictionaries

import net.nuclearprometheus.tpm.applicationserver.config.security.PolicyEnforcerPathsProvider
import net.nuclearprometheus.tpm.applicationserver.config.security.methodConfig
import net.nuclearprometheus.tpm.applicationserver.config.security.pathConfig
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.specification.CountrySpecificationBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CountryConfig {

    @Bean
    fun countrySpecificationBuilder() = CountrySpecificationBuilder

    @Bean
    fun countryPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/country"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:country:query")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/country/export"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:country:export")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/country/{code}"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:country:read")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/country/name/{name}"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:country:query")
                    }
                )
            }
        )
    }
}