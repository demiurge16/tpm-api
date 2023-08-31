package net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Id
import java.util.*

class PriorityId(value: UUID = UUID.randomUUID()): Id<UUID>(value)
