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
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project.security.ProjectPermissionService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.keycloak.representations.adapters.config.PolicyEnforcerConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProjectConfig(
    private val projectRepository: ProjectRepository,
    private val languageRepository: LanguageRepository,
    private val accuracyRepository: AccuracyRepository,
    private val industryRepository: IndustryRepository,
    private val unitRepository: UnitRepository,
    private val serviceTypeRepository: ServiceTypeRepository,
    private val currencyRepository: CurrencyRepository,
    private val clientRepository: ClientRepository,
    private val userRepository: UserRepository,
    private val projectPermissionService: ProjectPermissionService,
) {

    @Bean
    fun projectService(): ProjectService =
        ProjectServiceImpl(
            projectRepository,
            languageRepository,
            accuracyRepository,
            industryRepository,
            unitRepository,
            serviceTypeRepository,
            currencyRepository,
            clientRepository,
            userRepository,
            projectPermissionService,
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
                        scopes = mutableListOf("urn:tpm-backend:resource:project:query")
                    },
                    methodConfig {
                        method = "POST"
                        scopes = mutableListOf("urn:tpm-backend:resource:project:create")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/export"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:project:export")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{projectId}"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:project:read")
                    },
                    methodConfig {
                        method = "PUT"
                        scopes = mutableListOf("urn:tpm-backend:resource:project:update")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{projectId}/move-start"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:project:update")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{projectId}/move-deadline"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:project:update")
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
                        scopes = mutableListOf("urn:tpm-backend:resource:expense:query")
                    },
                    methodConfig {
                        method = "POST"
                        scopes = mutableListOf("urn:tpm-backend:resource:expense:create")
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
                        scopes = mutableListOf("urn:tpm-backend:resource:file:query")
                    },
                    methodConfig {
                        method = "POST"
                        scopes = mutableListOf("urn:tpm-backend:resource:file:create")
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
                        scopes = mutableListOf("urn:tpm-backend:resource:project:read", "urn:tpm-backend:resource:project:query")
                        scopesEnforcementMode = PolicyEnforcerConfig.ScopeEnforcementMode.ANY
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/refdata/team-member/role"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:project:read", "urn:tpm-backend:resource:project:query")
                        scopesEnforcementMode = PolicyEnforcerConfig.ScopeEnforcementMode.ANY
                    }
                )
            }
        )
    }

    @Bean
    fun projectStatusPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/project/{projectId}/finish-draft"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:project:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{projectId}/back-to-draft"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:project:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{projectId}/start-progress"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:project:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{projectId}/start-review"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:project:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{projectId}/approve"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:project:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{projectId}/reject"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:project:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{projectId}/back-to-progress"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:project:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{projectId}/deliver"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:project:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{projectId}/invoice"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:project:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{projectId}/pay"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:project:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{projectId}/put-on-hold"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:project:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{projectId}/resume"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:project:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{projectId}/cancel"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:project:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{projectId}/reopen"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("urn:tpm-backend:resource:project:manage")
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
                        scopes = mutableListOf("urn:tpm-backend:resource:task:query")
                    },
                    methodConfig {
                        method = "POST"
                        scopes = mutableListOf("urn:tpm-backend:resource:task:create")
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
                        scopes = mutableListOf("urn:tpm-backend:resource:project:manage")
                    },
                    methodConfig {
                        method = "POST"
                        scopes = mutableListOf("urn:tpm-backend:resource:project:manage")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/project/{projectId}/team-member/{teamMemberId}"
                methods = mutableListOf(
                    methodConfig {
                        method = "DELETE"
                        scopes = mutableListOf("urn:tpm-backend:resource:project:manage")
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
                        scopes = mutableListOf("urn:tpm-backend:resource:thread:query")
                    },
                    methodConfig {
                        method = "POST"
                        scopes = mutableListOf("urn:tpm-backend:resource:thread:create")
                    }
                )
            }
        )
    }
}