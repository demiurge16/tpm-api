package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientActiveStatusResponse
import java.util.UUID

interface ClientActiveStatusRequestHandler {
    fun activate(id: UUID): ClientActiveStatusResponse
    fun deactivate(id: UUID): ClientActiveStatusResponse
}