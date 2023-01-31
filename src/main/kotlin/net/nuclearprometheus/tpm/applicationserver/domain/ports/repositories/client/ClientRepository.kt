package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client

import net.nuclearprometheus.tpm.applicationserver.domain.model.client.Client
import java.util.*

interface ClientRepository {
    fun getAll(): List<Client>
    fun get(id: UUID): Client?
    fun create(clientType: Client): Client
    fun update(clientType: Client): Client
}