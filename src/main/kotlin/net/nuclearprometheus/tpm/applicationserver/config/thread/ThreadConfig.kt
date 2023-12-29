package net.nuclearprometheus.tpm.applicationserver.config.thread

import net.nuclearprometheus.tpm.applicationserver.config.security.PolicyEnforcerPathsProvider
import net.nuclearprometheus.tpm.applicationserver.config.security.methodConfig
import net.nuclearprometheus.tpm.applicationserver.config.security.pathConfig
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ThreadDislikeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ThreadLikeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ThreadRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.thread.ThreadService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.thread.ThreadServiceImpl
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.specification.TagSpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.specification.ThreadDislikeSpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.specification.ThreadLikeSpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.specification.ThreadSpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ThreadConfig(
    private val threadRepository: ThreadRepository,
    private val threadLikeRepository: ThreadLikeRepository,
    private val threadDislikeRepository: ThreadDislikeRepository,
    private val userRepository: UserRepository,
    private val userContextProvider: UserContextProvider,
    private val projectRepository: ProjectRepository
) {

    @Bean
    fun threadService(): ThreadService = ThreadServiceImpl(
        repository = threadRepository,
        threadLikeRepository = threadLikeRepository,
        threadDislikeRepository = threadDislikeRepository,
        userRepository = userRepository,
        userContextProvider = userContextProvider,
        projectRepository = projectRepository,
        logger = loggerFor(ThreadService::class.java)
    )

    @Bean
    fun threadSpecificationBuilder() = ThreadSpecificationBuilder

    @Bean
    fun threadLikeSpecificationBuilder() = ThreadLikeSpecificationBuilder

    @Bean
    fun threadDislikeSpecificationBuilder() = ThreadDislikeSpecificationBuilder

    @Bean
    fun tagSpecificationBuilder() = TagSpecificationBuilder

    @Bean
    fun threadPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/thread"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:thread:query")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/thread/{threadId}"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:thread:read")
                    },
                    methodConfig {
                        method = "PUT"
                        scopes = mutableListOf("urn:tpm-backend:resource:thread:update")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/thread/{threadId}/like"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:thread:interact")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/thread/{threadId}/unlike"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:thread:interact")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/thread/{threadId}/dislike"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:thread:interact")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/thread/{threadId}/undislike"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:thread:interact")
                    }
                )
            }
        )
    }

    @Bean
    fun threadReplyPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/thread/{threadId}/reply"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:reply:query")
                    },
                    methodConfig {
                        method = "POST"
                        scopes = mutableListOf("urn:tpm-backend:resource:reply:create")
                    }
                )
            }
        )
    }

    @Bean
    fun threadStatusPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/thread/{threadId}/activate"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:thread:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/thread/{threadId}/freeze"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:thread:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/thread/{threadId}/close"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:thread:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/thread/{threadId}/archive"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:thread:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/thread/{threadId}/delete"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:thread:manage")
                    }
                )
            }
        )
    }
}