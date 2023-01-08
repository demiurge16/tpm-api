package net.nuclearprometheus.translationprojectmanager.config.redis

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "app.cache")
class RedisCacheProperties(
    val countries: CacheProperties = CacheProperties(name = "countries-cache"),
    val languages: CacheProperties = CacheProperties(name = "languages-cache"),
) {
    data class CacheProperties(
        var ttl: Long = 86400,
        var name: String = "cache",
    )
}

