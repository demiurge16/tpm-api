package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.handlers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.responses.ClientTypeActiveStatusResponse
import java.util.*

interface ClientTypeActiveStatusRequestHandler {
    fun activate(id: UUID): ClientTypeActiveStatusResponse
    fun deactivate(id: UUID): ClientTypeActiveStatusResponse
}
