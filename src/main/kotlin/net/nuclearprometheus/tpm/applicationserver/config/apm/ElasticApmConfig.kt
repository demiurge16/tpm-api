package net.nuclearprometheus.tpm.applicationserver.config.apm

import co.elastic.apm.attach.ElasticApmAttacher.attach
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import jakarta.annotation.PostConstruct

@Configuration
@ConfigurationProperties(prefix = "elastic.apm")
@ConditionalOnProperty(value = ["elastic.apm.enabled"], havingValue = "true")
class ElasticApmConfig(
    var enabled: Boolean = false,
    var serverUrl: String = "",
    var serviceName: String = "",
    var secretToken: String = "",
    var applicationPackages: String = "",
    var logLevel: String = "",
) {

    @PostConstruct
    fun init() = attach(
        mapOf(
            "server_url" to serverUrl,
            "service_name" to serviceName,
            "secret_token" to secretToken,
            "application_packages" to applicationPackages,
            "log_level" to logLevel
        )
    )

}