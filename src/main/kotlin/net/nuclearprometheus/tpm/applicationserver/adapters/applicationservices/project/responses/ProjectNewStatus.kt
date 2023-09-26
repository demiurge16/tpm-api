package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses

import java.util.*

data class ProjectNewStatus(val projectId: UUID, val status: ProjectStatus)
