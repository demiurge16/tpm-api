package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.handlers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.responses.ClientActiveStatusResponse
import java.util.UUID

interface ClientActiveStatusRequestHandler {
    fun activate(id: UUID): ClientActiveStatusResponse
    fun deactivate(id: UUID): ClientActiveStatusResponse
}