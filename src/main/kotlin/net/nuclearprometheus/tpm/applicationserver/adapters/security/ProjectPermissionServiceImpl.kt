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

    override fun createProjectResource(projectId: ProjectId): Result<Unit> {
        try {
            keycloakAuthzClient.protection()
                .resource()
                .create(
                    ResourceRepresentation().apply {
                        name = "tpm-backend:project:${projectId.value}"
                        type = "tpm-backend:project"
                        displayName = "Manage project ${projectId.value}"
                        ownerManagedAccess = true
                        uris = setOf(
                            "/api/v1/project/${projectId.value}",
                            "/api/v1/project/${projectId.value}/*"
                        )
                        scopes = setOf(
                            ScopeRepresentation().apply {
                                name = "tpm-backend:project:read"
                            },
                            ScopeRepresentation().apply {
                                name = "tpm-backend:project:write"
                            },
                            ScopeRepresentation().apply {
                                name = "tpm-backend:project:manage"
                            }
                        )
                    }
                )
        } catch (e: Exception) {
            logger.error("Failed to create project resource for project ${projectId.value}", e)
            return Result.failure(e)
        }

        return Result.success(Unit)
    }

    override fun addUserProjectPermission(userId: UserId, projectId: ProjectId): Result<Unit> {
        try {
            val resourceId = keycloakAuthzClient.protection()
                .resource()
                .findByName("tpm-backend:project:${projectId.value}")
                .id

            val policyName = "tpm-backend:project:${projectId.value}:user:${userId.value}:read"
            keycloakAuthzClient.protection()
                .policy(resourceId)
                .create(
                    UmaPermissionRepresentation().apply {
                        name = policyName
                        description = "User ${userId.value} can read project ${projectId.value}"
                        type = "USER"
                        logic = Logic.POSITIVE
                        users = setOf(userId.toString())
                    }
                )
                .also {
                    logger.info("Created policy $policyName for user ${userId.value} and project ${projectId.value}")
                    logger.info("Policy: ${it.id}")
                }

            val permissionName = "tpm-backend:project:${projectId.value}:user:${userId.value}:permission:read"
            keycloakAuthzClient.protection()
                .policy(resourceId)
                .create(
                    UmaPermissionRepresentation().apply {
                        name = permissionName
                        description = "User ${userId.value} can read project ${projectId.value}"
                        type = "RESOURCE"
                        resources = setOf("tpm-backend:project:${projectId.value}")
                        policies = setOf(policyName)
                    }
                )
                .also {
                    logger.info("Created permission $permissionName for user ${userId.value} and project ${projectId.value}")
                    logger.info("Permission: ${it.id}")
                }

        } catch (e: Exception) {
            logger.error("Failed to add user project permission for user ${userId.value} and project ${projectId.value}", e)
            return Result.failure(e)
        }

        return Result.success(Unit)
    }

    override fun removeUserProjectPermission(userId: UserId, projectId: ProjectId): Result<Unit> {
        try {
            val resourceId = keycloakAuthzClient.protection()
                .resource()
                .findByName("tpm-backend:project:${projectId.value}")
                .id

            val policyName = "tpm-backend:project:${projectId.value}:user:${userId.value}:read"
            keycloakAuthzClient.protection()
                .policy(resourceId)
                .find(policyName, null, null, null)
                .first()
                .let {
                    keycloakAuthzClient.protection()
                        .policy(resourceId)
                        .delete(it.id)
                }

            val permissionName = "tpm-backend:project:${projectId.value}:user:${userId.value}:permission:read"
            keycloakAuthzClient.protection()
                .policy(resourceId)
                .find(permissionName, null, null, null)
                .first()
                .let {
                    keycloakAuthzClient.protection()
                        .policy(resourceId)
                        .delete(it.id)
                }

        } catch (e: Exception) {
            logger.error("Failed to remove user project permission for user ${userId.value} and project ${projectId.value}", e)
            return Result.failure(e)
        }

        return Result.success(Unit)
    }
}
