package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.handlers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.mappers.toView
import net.nuclearprometheus.translationprojectmanager.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.translationprojectmanager.domain.ports.repositories.client.ClientRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientViewRequestHandlerImpl(private val repository: ClientRepository) : ClientViewRequestHandler {

    override fun getClients() = repository.getAll().map { it.toView() }
    override fun getClient(id: UUID) = repository.get(id)?.toView() ?: throw NotFoundException("Client with id $id not found")
}