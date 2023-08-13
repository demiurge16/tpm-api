package net.nuclearprometheus.tpm.applicationserver.domain.model.thread

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Id
import java.util.*

class ReplyId(value: UUID = UUID.randomUUID()): Id<UUID>(value)
