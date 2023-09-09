package net.nuclearprometheus.tpm.applicationserver.config.redis

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
class RedisCacheProperties(cacheConfigurations: List<CacheProperties>) {
    val caches = cacheConfigurations

    data class CacheProperties(
        var ttl: Long = 86400,
        var name: String = "cache",
    )
}

