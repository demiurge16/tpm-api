package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.responses

import java.util.*

data class ClientTypeActiveStatusResponse(
    val id: UUID,
    val active: Boolean
)