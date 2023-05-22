package net.nuclearprometheus.tpm.applicationserver.config.user

import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.keycloak.KeycloakPrincipal
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@Configuration
class UserContextProviderConfig {

    private val logger = loggerFor(UserContextProviderConfig::class.java)

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
                else -> {
                    logger.info(principal::class.java.canonicalName)
                    throw RuntimeException("User not found")
                }
            }
        }
    }
}