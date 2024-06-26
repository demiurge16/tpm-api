package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.PriorityMapper.toActivityStatus
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.PriorityMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.CreatePriority
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.UpdatePriority
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Priority
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.PriorityId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.PriorityRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.PriorityService
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(propagation = Propagation.REQUIRED)
class PriorityApplicationService(
    private val repository: PriorityRepository,
    private val service: PriorityService,
    private val specificationBuilder: SpecificationBuilder<Priority>
) {
    private val logger = loggerFor(PriorityApplicationService::class.java)

    fun getPriorities(query: FilteredRequest<Priority>) = with(logger) {
        info("getPriorities($query)")
        repository.get(query.toQuery(specificationBuilder)).map { it.toView() }
    }

    fun getPriority(id: UUID) = with(logger) {
        info("getPriority($id)")

        repository.get(PriorityId(id))?.toView() ?: throw NotFoundException("Priority with id $id not found")
    }

    fun createPriority(request: CreatePriority) = with(logger) {
        info("createPriority($request)")

        service.create(request.name, request.description, request.emoji, request.value).toView()
    }

    fun updatePriority(id: UUID, request: UpdatePriority) = with(logger) {
        info("updatePriority($id, $request)")

        service.update(PriorityId(id), request.name, request.description, request.emoji, request.value).toView()
    }

    fun activatePriority(id: UUID) = with(logger) {
        info("activatePriority($id)")

        service.activate(PriorityId(id)).toActivityStatus()
    }

    fun deactivatePriority(id: UUID) = with(logger) {
        info("deactivatePriority($id)")

        service.deactivate(PriorityId(id)).toActivityStatus()
    }
}