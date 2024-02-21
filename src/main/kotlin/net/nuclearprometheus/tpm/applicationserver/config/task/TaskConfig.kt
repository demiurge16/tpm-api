package net.nuclearprometheus.tpm.applicationserver.config.task

import net.nuclearprometheus.tpm.applicationserver.config.security.PolicyEnforcerPathsProvider
import net.nuclearprometheus.tpm.applicationserver.config.security.methodConfig
import net.nuclearprometheus.tpm.applicationserver.config.security.pathConfig
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.task.TaskRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.task.TaskService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.task.TaskServiceImpl
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.specification.TaskSpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import org.keycloak.representations.adapters.config.PolicyEnforcerConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TaskConfig(
    private val taskRepository: TaskRepository,
    private val languageRepository: LanguageRepository,
    private val accuracyRepository: AccuracyRepository,
    private val industryRepository: IndustryRepository,
    private val unitRepository: UnitRepository,
    private val serviceTypeRepository: ServiceTypeRepository,
    private val currencyRepository: CurrencyRepository,
    private val priorityRepository: PriorityRepository,
    private val projectRepository: ProjectRepository,
    private val userRepository: UserRepository,
    private val userContextProvider: UserContextProvider
) {

    @Bean
    fun taskService(): TaskService =
        TaskServiceImpl(
            taskRepository,
            languageRepository,
            accuracyRepository,
            industryRepository,
            unitRepository,
            serviceTypeRepository,
            currencyRepository,
            priorityRepository,
            projectRepository,
            userRepository,
            userContextProvider,
            loggerFor(TaskService::class.java)
        )

    @Bean
    fun taskSpecificationBuilder() = TaskSpecificationBuilder

    @Bean
    fun taskPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/task"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:task:read")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/task/{taskId}"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:task:read")
                    },
                    methodConfig {
                        method = "PUT"
                        scopes = mutableListOf("urn:tpm-backend:resource:task:write")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/task/{taskId}/move-start"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:task:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/task/{taskId}/move-deadline"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:task:manage")
                    }
                )
            }
        )
    }

    @Bean
    fun taskAssignmentPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/task/{taskId}/assign-team-member"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:task:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/task/{taskId}/unassign-team-member"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:task:manage")
                    }
                )
            }
        )
    }

    @Bean
    fun taskPriorityPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/task/{taskId}/change-priority"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:task:manage")
                    }
                )
            }
        )
    }

    @Bean
    fun taskTimeEntryPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/task/{taskId}/time-entry"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:time-entry:read")
                    },
                    methodConfig {
                        method = "POST"
                        scopes = mutableListOf("urn:tpm-backend:resource:time-entry:create")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/task/{taskId}/time-entry/submitted"
                methods = mutableListOf(
                    methodConfig {
                        method = "POST"
                        scopes = mutableListOf("urn:tpm-backend:resource:time-entry:create")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/task/{taskId}/time-entry/export"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:time-entry:read")
                    }
                )
            }
        )
    }

    @Bean
    fun taskRefdataPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/task/refdata/status"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:task:read", "urn:tpm-backend:resource:task:query")
                        scopesEnforcementMode = PolicyEnforcerConfig.ScopeEnforcementMode.ANY
                    }
                )
            }
        )
    }

    @Bean
    fun taskStatusPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/task/{taskId}/start"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:task:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/task/{taskId}/start-review"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:task:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/task/{taskId}/reject"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:task:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/task/{taskId}/approve"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:task:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/task/{taskId}/put-on-hold"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:task:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/task/{taskId}/resume"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:task:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/task/{taskId}/complete"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:task:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/task/{taskId}/cancel"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:task:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/task/{taskId}/reopen"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:task:manage")
                    }
                )
            }
        )
    }
}