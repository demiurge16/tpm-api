package net.nuclearprometheus.tpm.applicationserver.config.redis

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "app.cache")
class RedisCacheProperties(
    val countries: CacheProperties = CacheProperties(name = "countries-cache"),
    val languages: CacheProperties = CacheProperties(name = "languages-cache"),
    val currencies: CacheProperties = CacheProperties(name = "currencies-cache"),
    val clientTypes: CacheProperties = CacheProperties(name = "client-types-cache"),
    val clients: CacheProperties = CacheProperties(name = "clients-cache")
) {
    data class CacheProperties(
        var ttl: Long = 86400,
        var name: String = "cache",
    )
}

