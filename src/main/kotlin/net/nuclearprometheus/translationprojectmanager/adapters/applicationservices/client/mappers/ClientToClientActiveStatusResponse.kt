package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.mappers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.responses.ClientActiveStatusResponse
import net.nuclearprometheus.translationprojectmanager.domain.model.client.Client

fun Client.toActiveStatusResponse() = ClientActiveStatusResponse(
    id = id.value,
    active = active
)