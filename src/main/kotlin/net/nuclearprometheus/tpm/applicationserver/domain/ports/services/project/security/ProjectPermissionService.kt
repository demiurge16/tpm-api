package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project.security

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User

interface ProjectPermissionService {

    fun createProjectResources(project: Project): Result<Unit>
    fun grantUserProjectPermission(user: User, project: Project, scope: ProjectScope): Result<Unit>
    fun revokeUserProjectPermission(user: User, project: Project, scope: ProjectScope): Result<Unit>
}
