package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.ServiceTypeMapper.toActivityStatus
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.ServiceTypeMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.ServiceType as ServiceTypeResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.CreateServiceTypes
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.UpdateServiceTypes
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.ServiceTypeStatus
import net.nuclearprometheus.tpm.applicationserver.adapters.controllers.dictionaries.ServiceTypeController
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ServiceType
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ServiceTypeId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.ServiceTypeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.ServiceTypeService
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(propagation = Propagation.REQUIRED)
class ServiceTypeApplicationService(
    private val service: ServiceTypeService,
    private val repository: ServiceTypeRepository
) {

    private val logger = loggerFor(ServiceTypeController::class.java)

    fun getServiceTypes(query: FilteredRequest<ServiceType>): Page<ServiceTypeResponse> {
        logger.info("getServiceTypes($query)")
        return repository.get(query.toQuery()).map { it.toView() }
    }

    fun getServiceType(serviceTypeId: UUID): ServiceTypeResponse {
        logger.info("getServiceType($serviceTypeId)")

        return repository.get(ServiceTypeId(serviceTypeId))?.toView()
            ?: throw NotFoundException("ServiceType with id $serviceTypeId not found")
    }

    fun createServiceType(request: CreateServiceTypes): ServiceTypeResponse {
        logger.info("createServiceType($request)")
        return service.create(request.name, request.description).toView()
    }

    fun updateServiceType(serviceTypeId: UUID, request: UpdateServiceTypes): ServiceTypeResponse {
        logger.info("updateServiceType($serviceTypeId, $request)")
        return service.update(ServiceTypeId(serviceTypeId), request.name, request.description).toView()
    }

    fun activateServiceType(serviceTypeId: UUID): ServiceTypeStatus {
        logger.info("activateServiceType($serviceTypeId)")
        return service.activate(ServiceTypeId(serviceTypeId)).toActivityStatus()
    }

    fun deactivateServiceType(serviceTypeId: UUID): ServiceTypeStatus {
        logger.info("deactivateServiceType($serviceTypeId)")
        return service.deactivate(ServiceTypeId(serviceTypeId)).toActivityStatus()
    }
}