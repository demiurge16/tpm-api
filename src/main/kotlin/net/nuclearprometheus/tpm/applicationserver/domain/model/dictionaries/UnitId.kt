package net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Id
import java.util.*

class UnitId(value: UUID = UUID.randomUUID()): Id<UUID>(value)
