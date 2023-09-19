package net.nuclearprometheus.tpm.applicationserver.config.redis

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit

@Configuration
class RedisCacheConfig(private val cacheProperties: RedisCacheProperties) {

    private val logger = loggerFor(this::class.java)

    @Bean
    fun cacheConfiguration() = RedisCacheConfiguration.defaultCacheConfig()

    @Bean
    fun redisCacheManagerBuilderCustomizer() =
        RedisCacheManagerBuilderCustomizer { builder ->
            logger.info("Configuring Redis cache manager")
            cacheProperties.caches.forEach { (ttl, name, unit) ->
                logger.info("Configuring Redis cache: $name with TTL: $ttl $unit")
                builder.defaultFor(name, ttl, ChronoUnit.valueOf(unit))
            }
        }

    private val kotlinModule = KotlinModule.Builder()
        .withReflectionCacheSize(512)
        .configure(KotlinFeature.NullToEmptyCollection, false)
        .configure(KotlinFeature.NullToEmptyMap, false)
        .configure(KotlinFeature.NullIsSameAsDefault, false)
        .configure(KotlinFeature.SingletonSupport, false)
        .configure(KotlinFeature.StrictNullChecks, false)
        .build()

    private val javaTimeModule = JavaTimeModule()

    private val typeValidator = BasicPolymorphicTypeValidator.builder()
        .allowIfBaseType(Any::class.java)
        .build()

    private val objectMapper = ObjectMapper()
        .registerModule(kotlinModule)
        .registerModule(javaTimeModule)
        .activateDefaultTyping(typeValidator, ObjectMapper.DefaultTyping.EVERYTHING)

    private fun duration(seconds: Long, unit: TemporalUnit) = Duration.of(seconds, unit)

    private fun RedisCacheManagerBuilder.defaultFor(name: String, ttl: Long, unit: TemporalUnit) =
        withCacheConfiguration(
            name,
            RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(duration(ttl, unit))
                .disableCachingNullValues()
                .serializeValuesWith(
                    SerializationPair.fromSerializer(
                        GenericJackson2JsonRedisSerializer(objectMapper)
                    )
                )
        )
}
