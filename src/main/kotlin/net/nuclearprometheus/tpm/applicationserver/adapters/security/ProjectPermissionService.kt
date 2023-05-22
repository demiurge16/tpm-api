package net.nuclearprometheus.tpm.applicationserver.adapters.security

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import org.keycloak.admin.client.Keycloak
import org.springframework.stereotype.Service

@Service
class ProjectPermissionService(private val keycloak: Keycloak) {

    fun addUserToProject(userId: UserId, projectId: ProjectId) {

    }
}
