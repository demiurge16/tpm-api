package net.nuclearprometheus.tpm.applicationserver.domain.model.chat

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Id
import java.util.UUID

class ChatId(value: UUID = UUID.randomUUID()): Id<UUID>(value)
