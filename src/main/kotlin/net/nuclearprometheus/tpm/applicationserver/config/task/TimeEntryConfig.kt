package net.nuclearprometheus.tpm.applicationserver.config.task

import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.config.security.PolicyEnforcerPathsProvider
import net.nuclearprometheus.tpm.applicationserver.config.security.methodConfig
import net.nuclearprometheus.tpm.applicationserver.config.security.pathConfig
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.specification.TimeEntrySpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.task.TaskRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.task.TimeEntryRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.task.TimeEntryService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.task.TimeEntryServiceImpl
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import org.keycloak.representations.adapters.config.PolicyEnforcerConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TimeEntryConfig(
    private val timeEntryRepository: TimeEntryRepository,
    private val userRepository: UserRepository,
    private val userContextProvider: UserContextProvider,
    private val projectRepository: ProjectRepository,
    private val taskRepository: TaskRepository
) {

    @Bean
    fun timeEntryService(): TimeEntryService =
        TimeEntryServiceImpl(
            timeEntryRepository = timeEntryRepository,
            userRepository = userRepository,
            userContextProvider = userContextProvider,
            projectRepository = projectRepository,
            taskRepository = taskRepository,
            logger = loggerFor(TimeEntryService::class.java)
        )

    @Bean
    fun timeEntrySpecificationBuilder() = TimeEntrySpecificationBuilder

    @Bean
    fun timeEntryPolicyEnforcerTaskProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/time-entry"
                methodConfig {
                    method = "GET"
                    scopes = listOf("urn:tpm-backend:resource:time-entry:read", "urn:tpm-backend:resource:time-entry:query")
                    scopesEnforcementMode = PolicyEnforcerConfig.ScopeEnforcementMode.ANY
                }
            },
            pathConfig {
                path = "/api/v1/time-entry/export"
                methodConfig {
                    method = "GET"
                    scopes = listOf("urn:tpm-backend:resource:time-entry:export")
                }
            },
            pathConfig {
                path = "/api/v1/time-entry/{timeEntryId}"
                methodConfig {
                    method = "GET"
                    scopes = listOf("urn:tpm-backend:resource:time-entry:read")
                }
                methodConfig {
                    method = "PUT"
                    scopes = listOf("urn:tpm-backend:resource:time-entry:update")
                }
                methodConfig {
                    method = "DELETE"
                    scopes = listOf("urn:tpm-backend:resource:time-entry:delete")
                }
            },
            pathConfig {
                path = "/api/v1/time-entry/{timeEntryId}/submit"
                methodConfig {
                    method = "PATCH"
                    scopes = listOf("urn:tpm-backend:resource:time-entry:update")
                }
            },
            pathConfig {
                path = "/api/v1/time-entry/{timeEntryId}/approve"
                methodConfig {
                    method = "PATCH"
                    scopes = listOf("urn:tpm-backend:resource:time-entry:update")
                }
            },
            pathConfig {
                path = "/api/v1/time-entry/{timeEntryId}/reject"
                methodConfig {
                    method = "PATCH"
                    scopes = listOf("urn:tpm-backend:resource:time-entry:update")
                }
            }
        )
    }

    @Bean
    fun timeEntryPolicyEnforcerRefDataProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/time-entry/refdata/status"
                methodConfig {
                    method = "GET"
                    scopes = listOf("urn:tpm-backend:resource:time-entry:read", "urn:tpm-backend:resource:time-entry:query")
                    scopesEnforcementMode = PolicyEnforcerConfig.ScopeEnforcementMode.ANY
                }
            },
            pathConfig {
                path = "/api/v1/time-entry/refdata/time-unit"
                methodConfig {
                    method = "GET"
                    scopes = listOf("urn:tpm-backend:resource:time-entry:read", "urn:tpm-backend:resource:time-entry:query")
                    scopesEnforcementMode = PolicyEnforcerConfig.ScopeEnforcementMode.ANY
                }
            }
        )
    }
}