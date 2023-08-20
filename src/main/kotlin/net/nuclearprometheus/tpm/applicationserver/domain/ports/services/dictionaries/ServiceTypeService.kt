package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ServiceType
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ServiceTypeId

interface ServiceTypeService {

    fun create(name: String, description: String): ServiceType
    fun update(id: ServiceTypeId, name: String, description: String): ServiceType
    fun activate(id: ServiceTypeId): ServiceType
    fun deactivate(id: ServiceTypeId): ServiceType
}

