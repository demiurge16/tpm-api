package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client

import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientType
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientTypeId
import java.util.UUID

interface ClientTypeRepository {
    fun getAll(): List<ClientType>
    fun get(id: ClientTypeId): ClientType?
    fun create(clientType: ClientType): ClientType
    fun update(clientType: ClientType): ClientType
}