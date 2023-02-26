package net.nuclearprometheus.tpm.applicationserver.config.security

import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecurityConfiguration(
    private val keycloakLogoutHandler: KeycloakLogoutHandler
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {

        http.cors().and().csrf().disable()
            .authorizeHttpRequests { it.anyRequest().authenticated() }
            .oauth2Login()
            .and()
            .logout()
            .addLogoutHandler(keycloakLogoutHandler)
            .logoutSuccessUrl("/")
            .and()
            .oauth2ResourceServer { it.jwt() }

        return http.build()
    }
}