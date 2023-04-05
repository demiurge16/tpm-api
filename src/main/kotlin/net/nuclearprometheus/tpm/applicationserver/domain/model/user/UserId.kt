package net.nuclearprometheus.tpm.applicationserver.domain.model.user

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Id
import java.util.*

class UserId(value: UUID = UUID.randomUUID()) : Id<UUID>(value)
