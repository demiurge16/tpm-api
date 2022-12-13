package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.handlers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.responses.ClientView
import java.util.UUID

interface ClientViewRequestHandler {

    fun getClients(): List<ClientView>
    fun getClient(id: UUID): ClientView

}