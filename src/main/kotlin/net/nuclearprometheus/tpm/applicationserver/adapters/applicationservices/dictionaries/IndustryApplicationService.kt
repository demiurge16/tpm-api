package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.singlePage
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.IndustryMapper.toActivityStatus
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.IndustryMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.IndustryRequest
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.IndustryId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.IndustryRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.IndustryService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.util.*

@Service
class IndustryApplicationService(
    private val service: IndustryService,
    private val repository: IndustryRepository
) {

    private val logger = loggerFor(IndustryApplicationService::class.java)

    fun getIndustries() = with(logger) {
        info("getIndustries()")

        singlePage(repository.getAll()).map { it.toView() }
    }

    fun getIndustry(id: UUID) = with(logger) {
        info("getIndustry($id)")

        repository.get(IndustryId(id))?.toView() ?: throw NotFoundException("Industry with id $id not found")
    }

    fun createIndustry(request: IndustryRequest.Create) = with(logger) {
        info("createIndustry($request)")

        service.create(request.name, request.description).toView()
    }

    fun updateIndustry(id: UUID, request: IndustryRequest.Update) = with(logger) {
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