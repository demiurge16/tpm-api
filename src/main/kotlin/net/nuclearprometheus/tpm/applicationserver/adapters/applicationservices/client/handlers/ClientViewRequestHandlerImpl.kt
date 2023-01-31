package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers.toView
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientViewRequestHandlerImpl(private val repository: ClientRepository) : ClientViewRequestHandler {

    override fun getClients() = repository.getAll().map { it.toView() }
    override fun getClient(id: UUID) = repository.get(id)?.toView() ?: throw NotFoundException("Client with id $id not found")
}