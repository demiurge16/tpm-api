package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ServiceType
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ServiceTypeId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.ServiceTypeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.logging.Logger

class ServiceTypeServiceImpl(
    private val serviceTypeRepository: ServiceTypeRepository,
    private val logger: Logger
) : ServiceTypeService {

    override fun create(name: String, description: String): ServiceType {
        val serviceType = ServiceType(name = name, description = description)
        return serviceTypeRepository.create(serviceType)
    }

    override fun update(id: ServiceTypeId, name: String, description: String): ServiceType {
        val serviceType = serviceTypeRepository.get(id) ?: throw NotFoundException("Service type with id $id does not exist")
        serviceType.update(name, description)

        return serviceTypeRepository.update(serviceType)
    }

    override fun activate(id: ServiceTypeId): ServiceType {
        val serviceType = serviceTypeRepository.get(id) ?: throw NotFoundException("Service type with id $id does not exist")
        serviceType.activate()

        return serviceTypeRepository.update(serviceType)
    }

    override fun deactivate(id: ServiceTypeId): ServiceType {
        val serviceType = serviceTypeRepository.get(id) ?: throw NotFoundException("Service type with id $id does not exist")
        serviceType.deactivate()

        return serviceTypeRepository.update(serviceType)
    }
}