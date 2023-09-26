package net.nuclearprometheus.tpm.applicationserver.domain.model.task

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Id
import java.util.*

class TimeEntryId(value: UUID = UUID.randomUUID()): Id<UUID>(value)
