package net.nuclearprometheus.tpm.applicationserver.config.user

import net.nuclearprometheus.tpm.applicationserver.config.security.PolicyEnforcerPathsProvider
import net.nuclearprometheus.tpm.applicationserver.config.security.methodConfig
import net.nuclearprometheus.tpm.applicationserver.config.security.pathConfig
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserRole
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.specification.UserSpecificationBuilder
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
    fun userSpecificationBuilder() = UserSpecificationBuilder

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
                        roles = principal.keycloakSecurityContext.token.realmAccess.roles.mapNotNull { it.toUserRole() }
                    )
                }
                is Jwt -> {
                    return User(
                        id = UserId(UUID.fromString(principal.subject)),
                        firstName = principal.claims["given_name"] as String,
                        lastName = principal.claims["family_name"] as String,
                        email = principal.claims["email"] as String,
                        username = principal.claims["preferred_username"] as String,
                        roles = ((principal.claims["realm_access"] as Map<*, *>)["roles"] as List<*>).mapNotNull { (it as String).toUserRole() }
                    )
                }
                else -> {
                    logger.error("Unknown principal type: ${principal.javaClass}")
                    throw IllegalStateException("Unknown principal type: ${principal.javaClass}")
                }
            }
        }
    }

    private fun String.toUserRole() =
        when (this) {
            "admin" -> UserRole.ADMIN
            "project-manager" -> UserRole.PROJECT_MANAGER
            "translator" -> UserRole.TRANSLATOR
            "editor" -> UserRole.EDITOR
            "proofreader" -> UserRole.PROOFREADER
            "subject-matter-expert" -> UserRole.SUBJECT_MATTER_EXPERT
            "publisher" -> UserRole.PUBLISHER
            "observer" -> UserRole.OBSERVER
            "user" -> UserRole.USER
            else -> null
        }

    @Bean
    fun userPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/user"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:user:query")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/user/export"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:user:export")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/user/{userId}"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:user:read")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/user/current"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:user:self")
                    }
                )
            }
        )
    }
}