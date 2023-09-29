package net.nuclearprometheus.tpm.applicationserver.config.security

import org.keycloak.adapters.authorization.integration.jakarta.ServletPolicyEnforcerFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
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
    private val keycloakProperties: KeycloakProperties,
    @Value("\${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}") private val jwkSetUri: String
) {

    @Bean
    @Order(1)
    fun actuatorFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.securityMatchers { it.requestMatchers("/actuator", "/actuator/**") }
            .authorizeHttpRequests { it.anyRequest().permitAll() }
            .cors { it.configurationSource { corsConfiguration() } }
            .csrf { it.disable() }

        return http.build()
    }

    @Bean
    @Order(2)
    fun swaggerFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.securityMatchers { it.requestMatchers("/swagger-ui", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**") }
            .authorizeHttpRequests { it.anyRequest().authenticated() }
            .oauth2Client {  }
            .oauth2Login {  }
            .logout { it.logoutSuccessUrl("/") }
            .cors { it.configurationSource { corsConfiguration() } }
            .csrf { it.disable() }

        return http.build()
    }

    @Bean
    @Order(3)
    fun apiFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.securityMatchers { it.requestMatchers("/api/**") }
            .authorizeHttpRequests { it.requestMatchers("/api/**").authenticated() }
            .oauth2ResourceServer { it.jwt(Customizer.withDefaults()) }
            .addFilterAfter(createPolicyEnforcerFilter(), BearerTokenAuthenticationFilter::class.java)
            .cors { it.configurationSource { corsConfiguration() } }
            .csrf { it.disable() }

        return http.build()
    }

    @Bean
    @Order(4)
    fun defaultFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests { it.anyRequest().permitAll() }
            .cors { it.configurationSource { corsConfiguration() } }
            .csrf { it.disable() }

        return http.build()
    }

    private fun corsConfiguration() =
        CorsConfiguration().apply {
            allowedOrigins = listOf(
                "http://localhost",
                "http://localhost:3000",
                "http://localhost:8080",
                "http://localhost:8081",
                "http://localhost:8082",
                "http://26.44.49.6",
                "http://26.44.49.6:3000",
                "http://26.44.49.6:8080",
                "http://26.44.49.6:8081",
                "http://26.44.49.6:8082"
            )
            allowedMethods = listOf("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            allowedHeaders = listOf("*")
            allowCredentials = true
        }

    private fun createPolicyEnforcerFilter() =
        ServletPolicyEnforcerFilter {
            policyEnforcerConfig {
                realm = keycloakProperties.realm
                authServerUrl = "http://26.44.49.6:8081"
                resource = keycloakProperties.resource
                credentials = mapOf(
                    "secret" to keycloakProperties.credentials.secret
                )
                paths = policyEnforcerPathsProviders.flatMap { it.paths }
            }
        }

    @Bean
    fun jwtDecoder(): JwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build()
}
