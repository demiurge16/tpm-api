package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project.security

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId

interface ProjectPermissionService {

    fun createProjectResources(projectId: ProjectId): Result<Unit>
    fun addUserProjectPermission(userId: UserId, projectId: ProjectId): Result<Unit>
    fun removeUserProjectPermission(userId: UserId, projectId: ProjectId): Result<Unit>
}
