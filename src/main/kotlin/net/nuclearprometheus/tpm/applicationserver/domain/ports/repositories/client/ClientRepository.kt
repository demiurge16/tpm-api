package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client

import net.nuclearprometheus.tpm.applicationserver.domain.model.client.Client
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository
import java.util.*

interface ClientRepository : BaseRepository<Client, ClientId>
