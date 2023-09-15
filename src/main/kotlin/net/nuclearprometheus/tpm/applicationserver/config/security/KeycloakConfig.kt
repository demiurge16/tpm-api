package net.nuclearprometheus.tpm.applicationserver.config.security

import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KeycloakConfig(
    @Value("\${app.keycloak.auth-server-url}") private val authServerUrl: String,
    @Value("\${app.keycloak.realm}") private val realm: String,
    @Value("\${app.keycloak.resource}") private val clientId: String,
    @Value("\${app.keycloak.credentials.secret}") private val secret: String
) {

    @Bean
    fun keycloak(): Keycloak {
        return KeycloakBuilder.builder()
            .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
            .serverUrl(authServerUrl)
            .realm(realm)
            .clientId(clientId)
            .clientSecret(secret)
            .build()
    }
}
