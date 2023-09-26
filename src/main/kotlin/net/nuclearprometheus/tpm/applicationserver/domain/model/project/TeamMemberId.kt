package net.nuclearprometheus.tpm.applicationserver.domain.model.project

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Id
import java.util.*

class TeamMemberId(value: UUID = UUID.randomUUID()): Id<UUID>(value)
