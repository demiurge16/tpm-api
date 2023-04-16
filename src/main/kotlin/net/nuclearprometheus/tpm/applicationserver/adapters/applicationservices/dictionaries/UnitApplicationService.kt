package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.singlePage
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.UnitMapper.toActivityStatus
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.UnitMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.UnitRequest
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.UnitId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.UnitRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.UnitService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.util.*

@Service
class UnitApplicationService(
    private val repository: UnitRepository,
    private val service: UnitService
) {

    private val logger = loggerFor(UnitApplicationService::class.java)

    fun getUnits() = with(logger) {
        info("getUnits()")

        singlePage(repository.getAll()).map { it.toView() }
    }

    fun getUnit(id: UUID) = with(logger) {
        info("getUnit($id)")

        repository.get(UnitId(id))?.toView() ?: throw NotFoundException("Unit with id $id not found")
    }

    fun createUnit(request: UnitRequest.Create) = with(logger) {
        info("createUnit($request)")

        service.create(request.name, request.description).toView()
    }

    fun updateUnit(id: UUID, request: UnitRequest.Update) = with(logger) {
        info("updateUnit($id, $request)")

        service.update(UnitId(id), request.name, request.description).toView()
    }

    fun activateUnit(id: UUID) = with(logger) {
        info("activateUnit($id)")

        service.activate(UnitId(id)).toActivityStatus()
    }

    fun deactivateUnit(id: UUID) = with(logger) {
        info("deactivateUnit($id)")

        service.deactivate(UnitId(id)).toActivityStatus()
    }
}