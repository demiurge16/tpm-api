package net.nuclearprometheus.tpm.applicationserver.config.redis

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit

@ConfigurationProperties(prefix = "app")
class RedisCacheProperties(cacheConfigurations: List<CacheProperties>) {
    val caches = cacheConfigurations

    data class CacheProperties(
        var ttl: Long = 86400,
        var name: String = "cache",
        var unit: String = "SECONDS"
    )
}

