package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.UnitMapper.toActivityStatus
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.UnitMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.UnitRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.UnitMeasurement
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Measurement
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Unit
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.UnitId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.UnitRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.UnitService
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(propagation = Propagation.REQUIRED)
class UnitApplicationService(
    private val repository: UnitRepository,
    private val service: UnitService
) {

    private val logger = loggerFor(UnitApplicationService::class.java)

    fun getUnits(query: FilteredRequest<Unit>) = with(logger) {
        info("getUnits($query)")
        repository.get(query.toQuery()).map { it.toView() }
    }

    fun getUnit(id: UUID) = with(logger) {
        info("getUnit($id)")
        repository.get(UnitId(id))?.toView() ?: throw NotFoundException("Unit with id $id not found")
    }

    fun createUnit(request: UnitRequest.Create) = with(logger) {
        info("createUnit($request)")

        service.create(request.name, request.description, request.volume, request.measurement).toView()
    }

    fun updateUnit(id: UUID, request: UnitRequest.Update) = with(logger) {
        info("updateUnit($id, $request)")

        service.update(UnitId(id), request.name, request.description, request.volume, request.measurement).toView()
    }

    fun activateUnit(id: UUID) = with(logger) {
        info("activateUnit($id)")

        service.activate(UnitId(id)).toActivityStatus()
    }

    fun deactivateUnit(id: UUID) = with(logger) {
        info("deactivateUnit($id)")

        service.deactivate(UnitId(id)).toActivityStatus()
    }

    fun getMeasurements() = with(logger) {
        info("getMeasurements()")

        Measurement.values().map { UnitMeasurement(it, it.title, it.description) }
    }
}