package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientView
import java.util.UUID

interface ClientViewRequestHandler {

    fun getClients(): List<ClientView>
    fun getClient(id: UUID): ClientView

}