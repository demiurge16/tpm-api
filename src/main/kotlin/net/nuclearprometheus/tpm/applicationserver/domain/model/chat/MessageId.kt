package net.nuclearprometheus.tpm.applicationserver.domain.model.chat

import java.util.UUID

data class MessageId(val value: UUID = UUID.randomUUID())
