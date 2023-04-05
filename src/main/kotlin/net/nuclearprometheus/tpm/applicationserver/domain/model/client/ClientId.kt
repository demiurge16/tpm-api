package net.nuclearprometheus.tpm.applicationserver.domain.model.client

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Id
import java.util.*

class ClientId(value: UUID = UUID.randomUUID()): Id<UUID>(value)
