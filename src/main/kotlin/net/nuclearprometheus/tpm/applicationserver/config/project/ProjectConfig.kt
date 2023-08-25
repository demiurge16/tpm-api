package net.nuclearprometheus.tpm.applicationserver.config.project

import net.nuclearprometheus.tpm.applicationserver.config.security.PolicyEnforcerPathsProvider
import net.nuclearprometheus.tpm.applicationserver.config.security.methodConfig
import net.nuclearprometheus.tpm.applicationserver.config.security.pathConfig
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project.ProjectService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project.ProjectServiceImpl
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProjectConfig(
    private val projectRepository: ProjectRepository,
    private val languageRepository: LanguageRepository,
    private val accuracyRepository: AccuracyRepository,
    private val industryRepository: IndustryRepository,
    private val unitRepository: UnitRepository,
    private val currencyRepository: CurrencyRepository,
    private val userRepository: UserRepository,
    private val clientRepository: ClientRepository
) {

    @Bean
    fun projectService(): ProjectService =
        ProjectServiceImpl(
            projectRepository,
            languageRepository,
            accuracyRepository,
            industryRepository,
            unitRepository,
            currencyRepository,
            clientRepository,
            userRepository,
            loggerFor(ProjectService::class.java)
        )

    @Bean
    fun projectPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/project"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:project:read")
                    },
                    methodConfig {
                        method = "POST"
                        scopes = mutableListOf("tpm-backend:project:write")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{id}"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:project:read")
                    },
                    methodConfig {
                        method = "PUT"
                        scopes = mutableListOf("tpm-backend:project:write")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{id}/move-start"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:project:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{id}/move-deadline"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:project:manage")
                    }
                )
            }
        )
    }

    @Bean
    fun projectExpensePolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/project/{projectId}/expense"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:expense:read")
                    },
                    methodConfig {
                        method = "POST"
                        scopes = mutableListOf("tpm-backend:expense:write")
                    }
                )
            }
        )
    }

    @Bean
    fun projectFilePolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/project/{projectId}/file"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:file:read")
                    },
                    methodConfig {
                        method = "POST"
                        scopes = mutableListOf("tpm-backend:file:write")
                    }
                )
            }
        )
    }

    @Bean
    fun projectRefdataPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/project/refdata/status"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:project:read")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/refdata/team-member/role"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:project:read")
                    }
                )
            }
        )
    }

    @Bean
    fun projectStatusPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/project/{id}/finish-draft"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:project:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{id}/back-to-draft"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:project:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{id}/start-progress"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:project:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{id}/finish-progress"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:project:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{id}/back-to-progress"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:project:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{id}/deliver"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:project:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{id}/invoice"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:project:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{id}/pay"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:project:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{id}/put-on-hold"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:project:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{id}/resume"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:project:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{id}/cancel"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:project:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{id}/reopen"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:project:manage")
                    }
                )
            }
        )
    }

    @Bean
    fun projectTaskPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/project/{projectId}/task"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:task:read")
                    },
                    methodConfig {
                        method = "POST"
                        scopes = mutableListOf("tpm-backend:task:write")
                    }
                )
            }
        )
    }

    @Bean
    fun projectTeamMemberPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/project/{projectId}/team-member"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:project:manage")
                    },
                    methodConfig {
                        method = "POST"
                        scopes = mutableListOf("tpm-backend:project:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{projectId}/team-member/{teamMemberId}"
                methods = mutableListOf(
                    methodConfig {
                        method = "DELETE"
                        scopes = mutableListOf("tpm-backend:project:manage")
                    }
                )
            }
        )
    }

    @Bean
    fun projectThreadPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/project/{projectId}/thread"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:thread:read")
                    },
                    methodConfig {
                        method = "POST"
                        scopes = mutableListOf("tpm-backend:thread:write")
                    }
                )
            }
        )
    }
}