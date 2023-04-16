package net.nuclearprometheus.tpm.applicationserver.config.user

import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import org.keycloak.KeycloakPrincipal
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@Configuration
class UserContextProviderConfig {

    @Bean
    fun userContextProvider(): UserContextProvider = object : UserContextProvider {
        override fun getCurrentUser(): User {
            val authenticationToken = SecurityContextHolder.getContext().authentication as KeycloakAuthenticationToken
            val keycloakPrincipal = authenticationToken.principal as KeycloakPrincipal<*>

            return User(
                id = UserId(UUID.fromString(keycloakPrincipal.keycloakSecurityContext.token.id)),
                firstName = keycloakPrincipal.keycloakSecurityContext.token.givenName,
                lastName = keycloakPrincipal.keycloakSecurityContext.token.familyName,
                email = keycloakPrincipal.keycloakSecurityContext.token.email,
                username = keycloakPrincipal.keycloakSecurityContext.token.preferredUsername,
            );
        }
    }
}