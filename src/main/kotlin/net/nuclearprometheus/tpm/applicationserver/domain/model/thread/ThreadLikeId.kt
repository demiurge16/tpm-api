package net.nuclearprometheus.tpm.applicationserver.domain.model.thread

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Id
import java.util.*

class ThreadLikeId(value: UUID = UUID.randomUUID()): Id<UUID>(value)