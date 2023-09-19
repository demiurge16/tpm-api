package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests

import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.ProjectRole
import java.util.*

sealed class ProjectTeamMemberRequest {
    data class Create(val userId: UUID, val role: ProjectRole) : ProjectTeamMemberRequest()
}