package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.client

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientType
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientTypeId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientTypeRepository

class ClientTypeServiceImpl(private val repository: ClientTypeRepository) : ClientTypeService {

    override fun create(name: String, description: String, corporate: Boolean): ClientType {
        val clientType = ClientType(
            name = name,
            description = description,
            corporate = corporate
        )

        return repository.create(clientType)
    }

    override fun update(id: ClientTypeId, name: String, description: String, corporate: Boolean): ClientType {
        val clientType = repository.get(id) ?: throw NotFoundException("Client type with id $id not found")
        clientType.update(name, description, corporate)

        return repository.update(clientType)
    }

    override fun activate(id: ClientTypeId): ClientType {
        val clientType = repository.get(id) ?: throw NotFoundException("Client type with id $id not found")
        clientType.activate()

        return repository.update(clientType)
    }

    override fun deactivate(id: ClientTypeId): ClientType {
        val clientType = repository.get(id) ?: throw NotFoundException("Client type with id $id not found")
        clientType.deactivate()

        return repository.update(clientType)
    }
}