package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client

import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientType
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientTypeId

interface ClientTypeService {
    fun create(name: String, description: String, corporate: Boolean): ClientType
    fun update(id: ClientTypeId, name: String, description: String, corporate: Boolean): ClientType
    fun activate(id: ClientTypeId): ClientType
    fun deactivate(id: ClientTypeId): ClientType
}