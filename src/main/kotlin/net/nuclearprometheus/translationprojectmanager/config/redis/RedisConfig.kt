package net.nuclearprometheus.translationprojectmanager.config.redis

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@Configuration
class RedisConfig {

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        val connectionFactory = LettuceConnectionFactory()
        connectionFactory.setDatabase(0)
        connectionFactory.hostName = "localhost"
        connectionFactory.port = 6379
        connectionFactory.setPassword("1qaz@WSX")
        return connectionFactory
    }

    @Bean
    fun redisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.setConnectionFactory(connectionFactory)
        return template
    }
}