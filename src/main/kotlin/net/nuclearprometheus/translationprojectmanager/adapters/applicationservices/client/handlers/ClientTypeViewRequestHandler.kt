package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.handlers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.responses.ClientTypeView
import java.util.*

interface ClientTypeViewRequestHandler {
    fun getClientTypes(): List<ClientTypeView>
    fun getClientType(id: UUID): ClientTypeView
}
