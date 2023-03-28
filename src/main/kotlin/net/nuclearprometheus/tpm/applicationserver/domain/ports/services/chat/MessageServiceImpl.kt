package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.chat

import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.ChatId
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.Message
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.chat.MessageRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.teammember.TeamMemberService
import org.webjars.NotFoundException

class MessageServiceImpl(
    private val messageRepository: MessageRepository,
    private val chatService: ChatService,
    private val teamMemberService: TeamMemberService
) : MessageService {

    override fun create(chatId: ChatId, author: TeamMemberId, content: String) {
        chatService.get(chatId) ?: throw NotFoundException("Chat does not exist")

        val message = Message(
            author = teamMemberService.get(author) ?: throw NotFoundException("Sender does not exist"),
            chatId = chatId,
            message = content
        )

        messageRepository.create(message)
    }
}