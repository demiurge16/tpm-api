package net.nuclearprometheus.tpm.applicationserver.domain.model.chat

import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMember
import java.time.ZonedDateTime

class Message(
    val id: MessageId = MessageId(),
    val author: TeamMember,
    val chatId: ChatId,
    val message: String,
    val timestamp: ZonedDateTime = ZonedDateTime.now()
)
