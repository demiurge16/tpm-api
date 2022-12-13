package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.mappers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.responses.ClientTypeCreateResponse
import net.nuclearprometheus.translationprojectmanager.domain.model.client.ClientType

fun ClientType.toCreateResponse() = ClientTypeCreateResponse(
    id = id.value,
    name = name,
    description = description,
    corporate = corporate,
    active = active
)
