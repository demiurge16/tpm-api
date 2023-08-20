package net.nuclearprometheus.tpm.applicationserver.config.user

import net.nuclearprometheus.tpm.applicationserver.config.security.PolicyEnforcerPathsProvider
import net.nuclearprometheus.tpm.applicationserver.config.security.methodConfig
import net.nuclearprometheus.tpm.applicationserver.config.security.pathConfig
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.keycloak.KeycloakPrincipal
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import java.util.*

@Configuration
class UserConfig {

    private val logger = loggerFor(UserConfig::class.java)

    @Bean
    fun userContextProvider(): UserContextProvider = object : UserContextProvider {
        override fun getCurrentUser(): User {
            val authenticationToken = SecurityContextHolder.getContext().authentication

            when (val principal = authenticationToken.principal) {
                is KeycloakPrincipal<*> -> {
                    return User(
                        id = UserId(UUID.fromString(principal.keycloakSecurityContext.token.id)),
                        firstName = principal.keycloakSecurityContext.token.givenName,
                        lastName = principal.keycloakSecurityContext.token.familyName,
                        email = principal.keycloakSecurityContext.token.email,
                        username = principal.keycloakSecurityContext.token.preferredUsername,
                    )
                }
                is Jwt -> {
                    return User(
                        id = UserId(UUID.fromString(principal.subject)),
                        firstName = principal.claims["given_name"] as String,
                        lastName = principal.claims["family_name"] as String,
                        email = principal.claims["email"] as String,
                        username = principal.claims["preferred_username"] as String,
                    )
                }
                else -> {
                    logger.error("Unknown principal type: ${principal.javaClass}")
                    throw IllegalStateException("Unknown principal type: ${principal.javaClass}")
                }
            }
        }
    }

    @Bean
    fun userPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/user"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:user:read")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/user/{userId}"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:user:read")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/user/current"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:user:read")
                    }
                )
            }
        )
    }
}