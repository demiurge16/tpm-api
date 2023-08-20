package net.nuclearprometheus.tpm.applicationserver.config.thread

import net.nuclearprometheus.tpm.applicationserver.config.security.PolicyEnforcerPathsProvider
import net.nuclearprometheus.tpm.applicationserver.config.security.methodConfig
import net.nuclearprometheus.tpm.applicationserver.config.security.pathConfig
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ThreadDislikeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ThreadLikeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ThreadRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.thread.ThreadService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.thread.ThreadServiceImpl
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ThreadConfig(
    private val threadRepository: ThreadRepository,
    private val threadLikeRepository: ThreadLikeRepository,
    private val threadDislikeRepository: ThreadDislikeRepository,
    private val teamMemberRepository: TeamMemberRepository,
    private val userRepository: UserRepository,
    private val projectRepository: ProjectRepository
) {

    @Bean
    fun threadService(): ThreadService = ThreadServiceImpl(
        repository = threadRepository,
        threadLikeRepository = threadLikeRepository,
        threadDislikeRepository = threadDislikeRepository,
        teamMemberRepository = teamMemberRepository,
        userRepository = userRepository,
        projectRepository = projectRepository,
        logger = loggerFor(ThreadService::class.java)
    )

    @Bean
    fun threadPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/thread"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:thread:read")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/thread/{id}"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:thread:read")
                    },
                    methodConfig {
                        method = "PUT"
                        scopes = mutableListOf("tpm-backend:thread:write")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/thread/{id}/like"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:thread:write")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/thread/{id}/unlike"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:thread:write")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/thread/{id}/dislike"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:thread:write")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/thread/{id}/undislike"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:thread:write")
                    }
                )
            }
        )
    }

    @Bean
    fun threadReplyPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/thread/{id}/reply"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:reply:read")
                    },
                    methodConfig {
                        method = "POST"
                        scopes = mutableListOf("tpm-backend:reply:write")
                    }
                )
            }
        )
    }

    @Bean
    fun threadStatusPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/thread/{id}/activate"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:thread:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/thread/{id}/freeze"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:thread:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/thread/{id}/close"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:thread:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/thread/{id}/archive"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:thread:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/thread/{id}/delete"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:thread:manage")
                    }
                )
            }
        )
    }
}