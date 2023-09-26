package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectRole
import java.util.*

data class TeamMemberProjectRole(
    val projectRoleId: UUID,
    val role: ProjectRole,
    val title: String,
    val description: String
)