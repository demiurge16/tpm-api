package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.handlers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.mappers.toView
import net.nuclearprometheus.translationprojectmanager.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.translationprojectmanager.domain.model.client.ClientTypeId
import net.nuclearprometheus.translationprojectmanager.domain.ports.repositories.client.ClientTypeRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientTypeViewRequestHandlerImpl(
    private val repository: ClientTypeRepository
) : ClientTypeViewRequestHandler {

    override fun getClientTypes() = repository.getAll().map { it.toView() }
    override fun getClientType(id: UUID) = repository.get(ClientTypeId(id))?.toView() ?: throw NotFoundException("Client type with id $id not found")
}