package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.responses

import java.util.UUID

data class ClientActiveStatusResponse(
    val id: UUID,
    val active: Boolean
)