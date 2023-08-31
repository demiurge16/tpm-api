package net.nuclearprometheus.tpm.applicationserver.domain.model.task

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Id
import java.util.*

class TaskId(value: UUID = UUID.randomUUID()): Id<UUID>(value)
