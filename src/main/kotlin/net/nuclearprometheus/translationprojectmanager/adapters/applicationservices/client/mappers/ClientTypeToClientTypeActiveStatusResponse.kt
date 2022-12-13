package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.mappers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.responses.ClientTypeActiveStatusResponse
import net.nuclearprometheus.translationprojectmanager.domain.model.client.ClientType

fun ClientType.toActiveStatusResponse() = ClientTypeActiveStatusResponse(
    id = id.value,
    active = active
)
