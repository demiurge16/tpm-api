package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client

import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientType
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientTypeId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository

interface ClientTypeRepository : BaseRepository<ClientType, ClientTypeId>