package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectRole
import java.util.*

data class CreateTeamMember(val userId: UUID, val role: ProjectRole)
