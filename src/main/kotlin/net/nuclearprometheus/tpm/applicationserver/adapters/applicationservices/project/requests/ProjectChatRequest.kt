package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests

import java.util.*

sealed class ProjectChatRequest {

    data class Create(
        val title: String,
        val description: String,
        val owner: UUID,
        val participants: List<UUID>,
    ) : ProjectChatRequest()
}