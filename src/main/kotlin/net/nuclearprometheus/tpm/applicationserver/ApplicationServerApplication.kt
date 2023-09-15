package net.nuclearprometheus.tpm.applicationserver

import net.nuclearprometheus.tpm.applicationserver.config.redis.RedisCacheProperties
import net.nuclearprometheus.tpm.applicationserver.config.security.KeycloakProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
@EnableCaching
@EnableConfigurationProperties(RedisCacheProperties::class, KeycloakProperties::class)
class ApplicationServerApplication

fun main(args: Array<String>) {
    runApplication<ApplicationServerApplication>(*args)
}
