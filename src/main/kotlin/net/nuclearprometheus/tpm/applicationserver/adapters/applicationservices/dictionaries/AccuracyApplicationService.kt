package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.controllers.dictionaries.AccuracyController
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.AccuracyMapper.toActivityStatus
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.AccuracyMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.AccuracyRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Accuracy
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.AccuracyId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.AccuracyRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.AccuracyService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccuracyApplicationService(
    private val service: AccuracyService,
    private val repository: AccuracyRepository
) {

    private val logger = loggerFor(AccuracyController::class.java)

    fun getAccuracies(query: FilteredRequest<Accuracy>) = with(logger) {
        info("getAccuracies($query)")
        repository.get(query.toQuery()).map { it.toView() }
    }

    fun getAccuracy(id: UUID) = with(logger) {
        info("getAccuracy($id)")

        repository.get(AccuracyId(id))?.toView() ?: throw NotFoundException("Accuracy with id $id not found")
    }

    fun createAccuracy(request: AccuracyRequest.Create) = with(logger) {
        info("createAccuracy($request)")

        service.create(
            name = request.name,
            description = request.description
        ).toView()
    }

    fun updateAccuracy(id: UUID, request: AccuracyRequest.Update) = with(logger) {
        info("updateAccuracy($id, $request)")

        service.update(
            id = AccuracyId(id),
            name = request.name,
            description = request.description
        ).toView()
    }

    fun activateAccuracy(id: UUID) = with(logger) {
        info("activateAccuracy($id)")

        service.activate(AccuracyId(id)).toActivityStatus()
    }

    fun deactivateAccuracy(id: UUID) = with(logger) {
        info("activateAccuracy($id)")

        service.activate(AccuracyId(id)).toActivityStatus()
    }
}