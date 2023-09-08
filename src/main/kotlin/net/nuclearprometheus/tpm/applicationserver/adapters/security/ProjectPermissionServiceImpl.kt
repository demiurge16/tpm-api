package net.nuclearprometheus.tpm.applicationserver.adapters.security

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project.security.ProjectPermissionService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project.security.ProjectScope
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

    override fun createProjectResources(project: Project): Result<Unit> {
        val projectId = project.id
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

    override fun grantUserProjectPermission(user: User, project: Project, scope: ProjectScope): Result<Unit> {
        val userId = user.id
        val projectId = project.id
        try {
            val resource = keycloakAuthzClient.protection()
                .resource()
                .findByName(scope.resourceName(projectId))

            if (resource == null) {
                logger.error("Failed to find resource ${scope.resourceName(projectId)} for project ${projectId.value}")
                return Result.failure(Exception("Failed to find resource ${scope.resourceName(projectId)} for project ${projectId.value}"))
            }

            val permissionName = scope.permissionName(userId, projectId)

            val permission = keycloakAuthzClient.protection()
                .policy(resource.id)
                .find(permissionName, null, null, null)
                .firstOrNull()

            if (permission != null) {
                logger.info("Permission $permissionName for user ${userId.value} and project ${projectId.value} already exists")
                return Result.success(Unit)
            }

            keycloakAuthzClient.protection()
                .policy(resource.id)
                .create(
                    UmaPermissionRepresentation().apply {
                        name = permissionName
                        description = "User ${userId.value} can read project ${projectId.value}"
                        logic = Logic.POSITIVE
                        resources = setOf(resource.id)
                        users = setOf(userId.toString())
                        scopes = setOf(scope.scopeName())
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

    override fun revokeUserProjectPermission(user: User, project: Project, scope: ProjectScope): Result<Unit> {
        val userId = user.id
        val projectId = project.id
        try {
            val resourceId = keycloakAuthzClient.protection()
                .resource()
                .findByName(scope.resourceName(projectId))
                .id

            val policyName = scope.permissionName(userId, projectId)
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

    private fun projectResourceId(projectId: ProjectId) = "urn:tpm-backend:resource:project:${projectId.value}"

    private fun projectResource(projectId: ProjectId): ResourceRepresentation {
        return ResourceRepresentation().apply {
            name = projectResourceId(projectId)
            type = "urn:tpm-backend:resource-type:project"
            displayName = "Manage project ${projectId.value}"
            ownerManagedAccess = true
            uris = setOf(
                "/api/v1/project/${projectId.value}",
                "/api/v1/project/${projectId.value}/*"
            )
            scopes = setOf(
                createScopeRepresentation(ProjectScope.READ),
                createScopeRepresentation(ProjectScope.UPDATE),
                createScopeRepresentation(ProjectScope.MANAGE),
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
                createScopeRepresentation(ProjectScope.QUERY_EXPENSES),
                createScopeRepresentation(ProjectScope.EXPORT_EXPENSES),
                createScopeRepresentation(ProjectScope.CREATE_EXPENSES),
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
                createScopeRepresentation(ProjectScope.QUERY_FILES),
                createScopeRepresentation(ProjectScope.EXPORT_FILES),
                createScopeRepresentation(ProjectScope.CREATE_FILES),
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
                createScopeRepresentation(ProjectScope.QUERY_TASKS),
                createScopeRepresentation(ProjectScope.EXPORT_TASKS),
                createScopeRepresentation(ProjectScope.CREATE_TASKS)
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
                createScopeRepresentation(ProjectScope.QUERY_THREADS),
                createScopeRepresentation(ProjectScope.CREATE_THREADS)
            )
        }
    }

    private fun createScopeRepresentation(scope: ProjectScope): ScopeRepresentation {
        return ScopeRepresentation().apply {
            name = scope.scopeName()
        }
    }

    private fun ProjectScope.scopeName() =
        when (this) {
            ProjectScope.READ -> "urn:tpm-backend:resource:project:read"
            ProjectScope.UPDATE -> "urn:tpm-backend:resource:project:update"
            ProjectScope.MANAGE -> "urn:tpm-backend:resource:project:manage"
            ProjectScope.QUERY_EXPENSES -> "urn:tpm-backend:resource:expense:query"
            ProjectScope.EXPORT_EXPENSES -> "urn:tpm-backend:resource:expense:export"
            ProjectScope.CREATE_EXPENSES -> "urn:tpm-backend:resource:expense:create"
            ProjectScope.QUERY_FILES -> "urn:tpm-backend:resource:file:query"
            ProjectScope.EXPORT_FILES -> "urn:tpm-backend:resource:file:export"
            ProjectScope.CREATE_FILES -> "urn:tpm-backend:resource:file:create"
            ProjectScope.QUERY_TASKS -> "urn:tpm-backend:resource:task:query"
            ProjectScope.EXPORT_TASKS -> "urn:tpm-backend:resource:task:export"
            ProjectScope.CREATE_TASKS -> "urn:tpm-backend:resource:task:create"
            ProjectScope.QUERY_THREADS -> "urn:tpm-backend:resource:thread:query"
            ProjectScope.CREATE_THREADS -> "urn:tpm-backend:resource:thread:create"
        }

    private fun ProjectScope.permissionName(userId: UserId, projectId: ProjectId) =
        when (this) {
            ProjectScope.READ -> "${resourceName(projectId)}:user:${userId.value}:permission:read"
            ProjectScope.UPDATE -> "${resourceName(projectId)}:user:${userId.value}:permission:update"
            ProjectScope.MANAGE -> "${resourceName(projectId)}:user:${userId.value}:permission:manage"
            ProjectScope.QUERY_EXPENSES -> "${resourceName(projectId)}:user:${userId.value}:permission:query"
            ProjectScope.EXPORT_EXPENSES -> "${resourceName(projectId)}:user:${userId.value}:permission:export"
            ProjectScope.CREATE_EXPENSES -> "${resourceName(projectId)}:user:${userId.value}:permission:create"
            ProjectScope.QUERY_FILES -> "${resourceName(projectId)}:user:${userId.value}:permission:query"
            ProjectScope.EXPORT_FILES -> "${resourceName(projectId)}:user:${userId.value}:permission:export"
            ProjectScope.CREATE_FILES -> "${resourceName(projectId)}:user:${userId.value}:permission:create"
            ProjectScope.QUERY_TASKS -> "${resourceName(projectId)}:user:${userId.value}:permission:query"
            ProjectScope.EXPORT_TASKS -> "${resourceName(projectId)}:user:${userId.value}:permission:export"
            ProjectScope.CREATE_TASKS -> "${resourceName(projectId)}:user:${userId.value}:permission:create"
            ProjectScope.QUERY_THREADS -> "${resourceName(projectId)}:user:${userId.value}:permission:query"
            ProjectScope.CREATE_THREADS -> "${resourceName(projectId)}:user:${userId.value}:permission:create"
        }

    private fun ProjectScope.resourceName(projectId: ProjectId) =
        when (this) {
            ProjectScope.READ, ProjectScope.UPDATE, ProjectScope.MANAGE ->
                projectResourceId(projectId)
            ProjectScope.QUERY_EXPENSES, ProjectScope.EXPORT_EXPENSES, ProjectScope.CREATE_EXPENSES ->
                projectExpensesResourceId(projectId)
            ProjectScope.QUERY_FILES, ProjectScope.EXPORT_FILES, ProjectScope.CREATE_FILES ->
                projectFilesResourceId(projectId)
            ProjectScope.QUERY_TASKS, ProjectScope.EXPORT_TASKS, ProjectScope.CREATE_TASKS ->
                projectTasksResourceId(projectId)
            ProjectScope.QUERY_THREADS, ProjectScope.CREATE_THREADS ->
                projectThreadsResourceId(projectId)
        }
}
