package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers.toView
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientTypeId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientTypeRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientTypeViewRequestHandlerImpl(
    private val repository: ClientTypeRepository
) : net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.handlers.ClientTypeViewRequestHandler {

    override fun getClientTypes() = repository.getAll().map { it.toView() }
    override fun getClientType(id: UUID) = repository.get(ClientTypeId(id))?.toView() ?: throw NotFoundException("Client type with id $id not found")
}