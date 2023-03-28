package net.nuclearprometheus.tpm.applicationserver.domain.model.chat

import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User
import java.time.ZonedDateTime

class Message(
    val id: MessageId = MessageId(),
    val author: User,
    val message: String,
    val timestamp: ZonedDateTime = ZonedDateTime.now()
)
