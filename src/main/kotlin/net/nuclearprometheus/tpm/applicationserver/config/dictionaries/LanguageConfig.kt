package net.nuclearprometheus.tpm.applicationserver.config.dictionaries

import net.nuclearprometheus.tpm.applicationserver.config.security.PolicyEnforcerPathsProvider
import net.nuclearprometheus.tpm.applicationserver.config.security.methodConfig
import net.nuclearprometheus.tpm.applicationserver.config.security.pathConfig
import org.keycloak.representations.adapters.config.PolicyEnforcerConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LanguageConfig {

    @Bean
    fun languagePolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/language"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:language:query")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/language/export"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:language:export")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/language/name/{name}"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:language:query")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/language/{code}"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:language:read")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/language/refdata/scope"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:language:read", "urn:tpm-backend:resource:language:query")
                        scopesEnforcementMode = PolicyEnforcerConfig.ScopeEnforcementMode.ANY
                    }
                )
            },
            pathConfig {
                path = "/api/v1/language/refdata/type"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:language:read", "urn:tpm-backend:resource:language:query")
                        scopesEnforcementMode = PolicyEnforcerConfig.ScopeEnforcementMode.ANY
                    }
                )
            }
        )
    }
}