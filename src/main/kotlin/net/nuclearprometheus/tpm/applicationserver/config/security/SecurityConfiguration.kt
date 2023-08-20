package net.nuclearprometheus.tpm.applicationserver.config.security

import org.keycloak.adapters.authorization.integration.jakarta.ServletPolicyEnforcerFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration


@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val policyEnforcerPathsProviders: List<PolicyEnforcerPathsProvider>,
    @Value("\${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}") private val jwkSetUri: String
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.cors { it.configurationSource { corsConfiguration() } }
            .csrf { it.disable() }
            .authorizeHttpRequests { it.anyRequest().authenticated() }
            .oauth2ResourceServer { it.jwt(Customizer.withDefaults()) }
            .addFilterAfter(createPolicyEnforcerFilter(), BearerTokenAuthenticationFilter::class.java)

        return http.build()
    }

    private fun corsConfiguration() =
        CorsConfiguration().apply {
            allowedOrigins = listOf(
                "http://localhost:3000",
                "http://localhost:8080",
                "http://localhost:8081",
                "http://26.44.49.6:3000",
                "http://26.44.49.6:8080",
                "http://26.44.49.6:8081"
            )
            allowedMethods = listOf("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            allowedHeaders = listOf("*")
            allowCredentials = true
        }

    private fun createPolicyEnforcerFilter() =
        ServletPolicyEnforcerFilter {
            policyEnforcerConfig {
                realm = "tpm"
                authServerUrl = "http://26.44.49.6:8081"
                resource = "tpm-backend"
                credentials = mapOf(
                    "secret" to "euRH66K7xvH2xkVtYE91GBAUG7J0KFfc"
                )
                paths = policyEnforcerPathsProviders.flatMap { it.paths }
            }
        }

    @Bean
    fun jwtDecoder(): JwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build()
}
