package net.nuclearprometheus.tpm.applicationserver.config.security

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.keycloak")
class KeycloakProperties(
    val realm: String,
    val authServerUrl: String,
    val resource: String,
    val credentials: Credentials
) {

    data class Credentials(
        val secret: String
    )
}
