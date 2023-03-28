package net.nuclearprometheus.tpm.applicationserver.domain.model.chat

import java.util.UUID

data class ChatId(val value: UUID = UUID.randomUUID())