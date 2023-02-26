package net.nuclearprometheus.tpm.applicationserver.config.security

import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class KeycloakLogoutHandler(private val webClientBuilder: WebClient.Builder) : LogoutHandler {

    private val logger = loggerFor(this::class.java)

    override fun logout(request: HttpServletRequest, response: HttpServletResponse, auth: Authentication) {
        val user = auth.principal as OidcUser

        val endSessionEndpoint = user.issuer.toString() + "/protocol/openid-connect/logout"
        val uri = UriComponentsBuilder
            .fromUriString(endSessionEndpoint)
            .queryParam("id_token_hint", user.idToken.tokenValue)
            .toUriString()

        webClientBuilder.build()
            .get()
            .uri(uri)
            .retrieve()
            .toBodilessEntity()
            .subscribe(
                {
                    if (it.statusCode.is2xxSuccessful) {
                        logger.info("Successfully logged out from Keycloak")
                    } else {
                        logger.error("Could not propagate logout to Keycloak")
                    }
                },
                {
                    logger.error("Could not propagate logout to Keycloak", it)
                }
            )
    }
}
