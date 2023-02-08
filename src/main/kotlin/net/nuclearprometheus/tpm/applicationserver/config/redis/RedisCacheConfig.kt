package net.nuclearprometheus.tpm.applicationserver.config.redis

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair
import java.time.Duration

@Configuration
class RedisCacheConfig {

    @Bean
    fun cacheConfiguration() = RedisCacheConfiguration.defaultCacheConfig()

    @Bean
    fun redisCacheManagerBuilderCustomizer(properties: RedisCacheProperties) = RedisCacheManagerBuilderCustomizer { builder ->

        properties.countries.let { (ttl, name) ->
            builder.defaultFor(name, ttl)
        }

        properties.languages.let { (ttl, name) ->
            builder.defaultFor(name, ttl)
        }

        properties.currencies.let { (ttl, name) ->
            builder.defaultFor(name, ttl)
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

    private fun ofSeconds(seconds: Long) = Duration.ofSeconds(seconds)

    private fun RedisCacheManagerBuilder.defaultFor(name: String, ttl: Long) = withCacheConfiguration(
        name,
        RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(ofSeconds(ttl))
            .disableCachingNullValues()
            .serializeValuesWith(
                SerializationPair.fromSerializer(
                    GenericJackson2JsonRedisSerializer(objectMapper)
                )
            )
    )
}
