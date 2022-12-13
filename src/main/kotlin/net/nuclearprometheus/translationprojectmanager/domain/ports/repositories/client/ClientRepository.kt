package net.nuclearprometheus.translationprojectmanager.domain.ports.repositories.client

import net.nuclearprometheus.translationprojectmanager.domain.model.client.Client
import java.util.*

interface ClientRepository {
    fun getAll(): List<Client>
    fun get(id: UUID): Client?
    fun create(clientType: Client): Client
    fun update(clientType: Client): Client
}