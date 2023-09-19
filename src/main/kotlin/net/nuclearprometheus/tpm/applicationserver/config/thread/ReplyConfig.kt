package net.nuclearprometheus.tpm.applicationserver.config.thread

import net.nuclearprometheus.tpm.applicationserver.config.security.PolicyEnforcerPathsProvider
import net.nuclearprometheus.tpm.applicationserver.config.security.methodConfig
import net.nuclearprometheus.tpm.applicationserver.config.security.pathConfig
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ReplyDislikeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ReplyLikeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ReplyRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ThreadRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.thread.ReplyService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.thread.ReplyServiceImpl
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ReplyConfig(
    private val replyRepository: ReplyRepository,
    private val replyLikeRepository: ReplyLikeRepository,
    private val replyDislikeRepository: ReplyDislikeRepository,
    private val threadRepository: ThreadRepository,
    private val userRepository: UserRepository
) {

    @Bean
    fun replyService(): ReplyService = ReplyServiceImpl(
        repository = replyRepository,
        replyLikeRepository = replyLikeRepository,
        replyDislikeRepository = replyDislikeRepository,
        threadRepository = threadRepository,
        userRepository = userRepository,
        logger = loggerFor(ReplyService::class.java)
    )

    @Bean
    fun replyPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/reply/{replyId}"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:reply:read")
                    },
                    methodConfig {
                        method = "PUT"
                        scopes = mutableListOf("urn:tpm-backend:resource:reply:write")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/reply/{replyId}/like"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:reply:interact")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/reply/{replyId}/unlike"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:reply:interact")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/reply/{replyId}/dislike"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:reply:interact")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/reply/{replyId}/undislike"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:reply:interact")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/reply/{replyId}"
                methods = mutableListOf(
                    methodConfig {
                        method = "DELETE"
                        scopes = mutableListOf("urn:tpm-backend:resource:reply:delete")
                    }
                )
            }
        )
    }
}