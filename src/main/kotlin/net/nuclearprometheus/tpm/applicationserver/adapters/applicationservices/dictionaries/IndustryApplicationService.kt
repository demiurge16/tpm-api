package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.IndustryMapper.toActivityStatus
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.IndustryMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.CreateIndustry
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.UpdateIndustry
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Industry
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.IndustryId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.IndustryRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.IndustryService
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(propagation = Propagation.REQUIRED)
class IndustryApplicationService(
    private val service: IndustryService,
    private val repository: IndustryRepository,
    private val specificationBuilder: SpecificationBuilder<Industry>
) {

    private val logger = loggerFor(IndustryApplicationService::class.java)

    fun getIndustries(query: FilteredRequest<Industry>) = with(logger) {
        info("getIndustries($query)")
        repository.get(query.toQuery(specificationBuilder)).map { it.toView() }
    }

    fun getIndustry(id: UUID) = with(logger) {
        info("getIndustry($id)")
        repository.get(IndustryId(id))?.toView() ?: throw NotFoundException("Industry with id $id not found")
    }

    fun createIndustry(request: CreateIndustry) = with(logger) {
        info("createIndustry($request)")

        service.create(request.name, request.description).toView()
    }

    fun updateIndustry(id: UUID, request: UpdateIndustry) = with(logger) {
        info("updateIndustry($id, $request)")

        service.update(IndustryId(id), request.name, request.description).toView()
    }

    fun activateIndustry(id: UUID) = with(logger) {
        info("activateIndustry($id)")

        service.activate(IndustryId(id)).toActivityStatus()
    }

    fun deactivateIndustry(id: UUID) = with(logger) {
        info("deactivateIndustry($id)")

        service.deactivate(IndustryId(id)).toActivityStatus()
    }
}