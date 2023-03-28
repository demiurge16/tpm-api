package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.chat

import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.ChatId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId

class MessageServiceImpl : MessageService {
    override fun create(chatId: ChatId, sender: TeamMemberId, content: String) {
        TODO("Not yet implemented")
    }
}