package net.nuclearprometheus.tpm.applicationserver.config.task

import net.nuclearprometheus.tpm.applicationserver.config.security.PolicyEnforcerPathsProvider
import net.nuclearprometheus.tpm.applicationserver.config.security.methodConfig
import net.nuclearprometheus.tpm.applicationserver.config.security.pathConfig
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.task.TaskRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.task.TaskService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.task.TaskServiceImpl
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TaskConfig(
    private val taskRepository: TaskRepository,
    private val languageRepository: LanguageRepository,
    private val accuracyRepository: AccuracyRepository,
    private val industryRepository: IndustryRepository,
    private val unitRepository: UnitRepository,
    private val currencyRepository: CurrencyRepository,
    private val priorityRepository: PriorityRepository,
    private val projectRepository: ProjectRepository,
    private val teamMemberRepository: TeamMemberRepository,
    private val userRepository: UserRepository
) {

    @Bean
    fun taskService(): TaskService =
        TaskServiceImpl(
            taskRepository,
            languageRepository,
            accuracyRepository,
            industryRepository,
            unitRepository,
            currencyRepository,
            priorityRepository,
            projectRepository,
            teamMemberRepository,
            userRepository,
            loggerFor(TaskService::class.java)
        )

    @Bean
    fun taskPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/task"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:task:read")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/task/{taskId}"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:task:read")
                    },
                    methodConfig {
                        method = "PUT"
                        scopes = mutableListOf("tpm-backend:task:write")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/task/{taskId}/move-start"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:task:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/task/{taskId}/move-deadline"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:task:manage")
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
                        scopes = mutableListOf("tpm-backend:task:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/task/{taskId}/unassign-team-member"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:task:manage")
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
                        scopes = mutableListOf("tpm-backend:task:manage")
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
                        scopes = mutableListOf("tpm-backend:task:read")
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
                        scopes = mutableListOf("tpm-backend:task:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/task/{taskId}/complete"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:task:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/task/{taskId}/request-revisions"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:task:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/task/{taskId}/complete-revisions"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:task:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/task/{taskId}/deliver"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:task:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/task/{taskId}/cancel"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:task:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/task/{taskId}/reopen"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:task:manage")
                    }
                )
            }
        )
    }
}