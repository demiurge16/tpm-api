package net.nuclearprometheus.tpm.applicationserver.domain.model.chat

import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMember
import java.time.ZonedDateTime

class Message(
    val id: MessageId = MessageId(),
    val message: String,
    val author: TeamMember,
    val chatId: ChatId,
    val timestamp: ZonedDateTime = ZonedDateTime.now()
)
