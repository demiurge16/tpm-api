package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses

import java.util.*

sealed class ProjectStatusResponse {

    data class NewStatus(val projectId: UUID, val status: Status) : ProjectStatusResponse()
}