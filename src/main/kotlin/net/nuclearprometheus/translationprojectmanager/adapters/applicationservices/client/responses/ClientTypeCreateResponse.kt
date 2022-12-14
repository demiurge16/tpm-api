package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.responses

import java.util.UUID

data class ClientTypeCreateResponse(
    val id: UUID,
    val name: String,
    val description: String,
    val corporate: Boolean,
    val active: Boolean
)
