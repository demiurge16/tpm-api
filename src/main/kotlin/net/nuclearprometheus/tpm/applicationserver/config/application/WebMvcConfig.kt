package net.nuclearprometheus.tpm.applicationserver.config.application

import net.nuclearprometheus.tpm.applicationserver.config.logging.CorrelationIdInterceptor
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
