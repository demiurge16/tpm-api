package net.nuclearprometheus.translationprojectmanager.config

import net.nuclearprometheus.translationprojectmanager.config.logging.CorrelationIdInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(
    private val correlationIdInterceptor: CorrelationIdInterceptor
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(correlationIdInterceptor)
    }
}
