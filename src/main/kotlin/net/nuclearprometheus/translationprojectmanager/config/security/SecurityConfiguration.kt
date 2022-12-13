package net.nuclearprometheus.translationprojectmanager.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class SecurityConfiguration {

    @Bean
    fun httpSecurityConfig() = WebSecurityCustomizer {
        it.ignoring().antMatchers("/api/v1/**", "api/v1**")
    }

    @Bean
    fun corsFilter(): CorsFilter {
        val config = CorsConfiguration()
        config.let {
            it.addAllowedOrigin("*")
            it.addAllowedHeader("*")
            it.addAllowedMethod("*")
        }

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)

        return CorsFilter(source)
    }
}
