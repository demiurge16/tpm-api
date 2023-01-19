package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientTypeActiveStatusResponse
import java.util.*

interface ClientTypeActiveStatusRequestHandler {
    fun activate(id: UUID): ClientTypeActiveStatusResponse
    fun deactivate(id: UUID): ClientTypeActiveStatusResponse
}
