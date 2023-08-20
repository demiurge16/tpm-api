package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ServiceType
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ServiceTypeId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository

interface ServiceTypeRepository : BaseRepository<ServiceType, ServiceTypeId>
