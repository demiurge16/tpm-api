package net.nuclearprometheus.tpm.applicationserver.adapters.security

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project.security.ProjectPermissionService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.keycloak.authorization.client.AuthzClient
import org.keycloak.authorization.client.Configuration
import org.keycloak.representations.idm.authorization.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class ProjectPermissionServiceImpl(
    @Value("\${keycloak.auth-server-url}") private val authServerUrl: String,
    @Value("\${keycloak.realm}") private val realm: String,
    @Value("\${keycloak.resource}") private val clientId: String,
    @Value("\${keycloak.credentials.secret}") private val secret: String
) : ProjectPermissionService {

    private val logger = loggerFor(ProjectPermissionService::class.java)
    private val keycloakAuthzClient = AuthzClient.create(
        Configuration(authServerUrl, realm, clientId, mapOf("secret" to secret), null)
    )

    override fun createProjectResources(projectId: ProjectId): Result<Unit> {
        val resourcesToCreate = listOf(
            projectResource(projectId),
            projectExpensesResource(projectId),
            projectFilesResource(projectId),
            projectTasksResource(projectId),
            projectThreadsResource(projectId)
        )

        try {
            resourcesToCreate.forEach {
                logger.info("Creating resource ${it.name}")
                keycloakAuthzClient.protection()
                    .resource()
                    .create(it)
            }

            return Result.success(Unit)
        } catch (e: Throwable) {
            logger.error("Failed to create project resources for project ${projectId.value}", e)
            resourcesToCreate.forEach {
                try {
                    logger.info("Deleting resource ${it.name}")
                    keycloakAuthzClient.protection()
                        .resource()
                        .delete(it.name)
                } catch (e: Throwable) {
                    logger.error("Failed to delete resource ${it.name}", e)
                }
            }

            return Result.failure(e)
        }
    }

    private fun projectResourceId(projectId: ProjectId) = "urn:tpm-backend:resource:project:${projectId.value}"

    private fun projectResource(projectId: ProjectId): ResourceRepresentation {
        return ResourceRepresentation().apply {
            name = projectResourceId(projectId)
            type = "urn:tpm-backend:resource:project"
            displayName = "Manage project ${projectId.value}"
            ownerManagedAccess = true
            uris = setOf(
                "/api/v1/project/${projectId.value}",
                "/api/v1/project/${projectId.value}/export",
                "/api/v1/project/${projectId.value}/finish-draft",
                "/api/v1/project/${projectId.value}/back-to-draft",
                "/api/v1/project/${projectId.value}/start-progress",
                "/api/v1/project/${projectId.value}/start-review",
                "/api/v1/project/${projectId.value}/approve",
                "/api/v1/project/${projectId.value}/reject",
                "/api/v1/project/${projectId.value}/deliver",
                "/api/v1/project/${projectId.value}/invoice",
                "/api/v1/project/${projectId.value}/pay",
                "/api/v1/project/${projectId.value}/put-on-hold",
                "/api/v1/project/${projectId.value}/resume",
                "/api/v1/project/${projectId.value}/cancel",
                "/api/v1/project/${projectId.value}/reopen",
                "/api/v1/project/${projectId.value}/refdata/status",
                "/api/v1/project/${projectId.value}/refdata/team-member/role",
                "/api/v1/project/${projectId.value}/team-member",
                "/api/v1/project/${projectId.value}/team-member/{teamMemberId}",
            )
            scopes = setOf(
                scope("urn:tpm-backend:resource:project:read"),
                scope("urn:tpm-backend:resource:project:write"),
                scope("urn:tpm-backend:resource:project:manage")
            )
        }
    }

    private fun projectExpensesResourceId(projectId: ProjectId) = "urn:tpm-backend:resource:project:${projectId.value}:expense"

    private fun projectExpensesResource(projectId: ProjectId): ResourceRepresentation {
        return ResourceRepresentation().apply {
            name = projectExpensesResourceId(projectId)
            type = "urn:tpm-backend:resource:project:expense"
            displayName = "Manage project ${projectId.value} expenses"
            ownerManagedAccess = true
            uris = setOf(
                "/api/v1/project/${projectId.value}/expense",
                "/api/v1/project/${projectId.value}/expense/export"
            )
            scopes = setOf(
                scope("urn:tpm-backend:resource:expense:read"),
                scope("urn:tpm-backend:resource:expense:write"),
                scope("urn:tpm-backend:resource:expense:manage")
            )
        }
    }

    private fun projectFilesResourceId(projectId: ProjectId) = "urn:tpm-backend:resource:project:${projectId.value}:file"

    private fun projectFilesResource(projectId: ProjectId): ResourceRepresentation {
        return ResourceRepresentation().apply {
            name = projectFilesResourceId(projectId)
            type = "urn:tpm-backend:resource:project:file"
            displayName = "Manage project ${projectId.value} files"
            ownerManagedAccess = true
            uris = setOf(
                "/api/v1/project/${projectId.value}/file",
                "/api/v1/project/${projectId.value}/file/export"
            )
            scopes = setOf(
                scope("urn:tpm-backend:resource:file:read"),
                scope("urn:tpm-backend:resource:file:write"),
                scope("urn:tpm-backend:resource:file:manage")
            )
        }
    }

    private fun projectTasksResourceId(projectId: ProjectId) = "urn:tpm-backend:resource:project:${projectId.value}:task"

    private fun projectTasksResource(projectId: ProjectId): ResourceRepresentation {
        return ResourceRepresentation().apply {
            name = projectTasksResourceId(projectId)
            type = "urn:tpm-backend:resource:project:task"
            displayName = "Manage project ${projectId.value} tasks"
            ownerManagedAccess = true
            uris = setOf(
                "/api/v1/project/${projectId.value}/task",
                "/api/v1/project/${projectId.value}/task/export"
            )
            scopes = setOf(
                scope("urn:tpm-backend:resource:task:read"),
                scope("urn:tpm-backend:resource:task:write"),
                scope("urn:tpm-backend:resource:task:manage")
            )
        }
    }

    private fun projectThreadsResourceId(projectId: ProjectId) = "urn:tpm-backend:resource:project:${projectId.value}:thread"

    private fun projectThreadsResource(projectId: ProjectId): ResourceRepresentation {
        return ResourceRepresentation().apply {
            name = projectThreadsResourceId(projectId)
            type = "urn:tpm-backend:resource:project:thread"
            displayName = "Manage project ${projectId.value} threads"
            ownerManagedAccess = true
            uris = setOf(
                "/api/v1/project/${projectId.value}/thread"
            )
            scopes = setOf(
                scope("urn:tpm-backend:resource:thread:read"),
                scope("urn:tpm-backend:resource:thread:write"),
                scope("urn:tpm-backend:resource:thread:manage")
            )
        }
    }

    private fun scope(scopeName: String): ScopeRepresentation {
        return ScopeRepresentation().apply {
            name = scopeName
        }
    }

    override fun addUserProjectPermission(userId: UserId, projectId: ProjectId): Result<Unit> {
        try {
            val resourceId = keycloakAuthzClient.protection()
                .resource()
                .findByName("urn:tpm-backend:resource:project:${projectId.value}")
                .id

            val permissionName = "urn:tpm-backend:resource:project:${projectId.value}:user:${userId.value}:permission:read"
            keycloakAuthzClient.protection()
                .policy(resourceId)
                .create(
                    UmaPermissionRepresentation().apply {
                        name = permissionName
                        description = "User ${userId.value} can read project ${projectId.value}"
                        logic = Logic.POSITIVE
                        resources = setOf(resourceId)
                        users = setOf(userId.toString())
                        scopes = setOf(
                            "urn:tpm-backend:resource:project:read",
                            "urn:tpm-backend:resource:project:write",
                        )
                    }
                )
                .also {
                    logger.info("Created permission $permissionName for user ${userId.value} and project ${projectId.value}")
                    logger.info("Permission: ${it.id}")
                }
        } catch (e: Throwable) {
            logger.error("Failed to add user project permission for user ${userId.value} and project ${projectId.value}", e)
            return Result.failure(e)
        }

        return Result.success(Unit)
    }

    override fun removeUserProjectPermission(userId: UserId, projectId: ProjectId): Result<Unit> {
        try {
            val resourceId = keycloakAuthzClient.protection()
                .resource()
                .findByName("urn:tpm-backend:resource:project:${projectId.value}")
                .id

            val policyName = "urn:tpm-backend:resource:project:${projectId.value}:user:${userId.value}:read"
            keycloakAuthzClient.protection()
                .policy(resourceId)
                .find(policyName, null, null, null)
                .first()
                .let {
                    keycloakAuthzClient.protection()
                        .policy(resourceId)
                        .delete(it.id)
                }

            val permissionName = "urn:tpm-backend:resource:project:${projectId.value}:user:${userId.value}:permission:read"
            keycloakAuthzClient.protection()
                .policy(resourceId)
                .find(permissionName, null, null, null)
                .first()
                .let {
                    keycloakAuthzClient.protection()
                        .policy(resourceId)
                        .delete(it.id)
                }
        } catch (e: Throwable) {
            logger.error("Failed to remove user project permission for user ${userId.value} and project ${projectId.value}", e)
            return Result.failure(e)
        }

        return Result.success(Unit)
    }
}
