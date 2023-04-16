package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses

import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberRole
import java.util.*

sealed class ProjectTeamMemberResponse {

    data class View(
        val id: UUID,
        val userId: UUID,
        val firstName: String,
        val lastName: String,
        val email: String,
        val role: TeamMemberRoleView,
        val projectId: UUID
    ) : ProjectTeamMemberResponse() {

        data class TeamMemberRoleView(
            val role: TeamMemberRole,
            val title: String,
            val description: String
        )
    }
}