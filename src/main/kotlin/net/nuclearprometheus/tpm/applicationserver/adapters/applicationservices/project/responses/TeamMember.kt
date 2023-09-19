package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses

import java.util.*

data class TeamMember(
    val userId: UUID,
    val firstName: String,
    val lastName: String,
    val email: String,
    val roles: List<TeamMemberProjectRole>,
    val project: ProjectShortView
)
