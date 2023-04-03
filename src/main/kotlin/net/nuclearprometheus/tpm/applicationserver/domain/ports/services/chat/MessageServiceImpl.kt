package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.chat

import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.ChatId
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.Message
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.chat.ChatRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.chat.MessageRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.logging.Logger
import org.webjars.NotFoundException

class MessageServiceImpl(
    private val messageRepository: MessageRepository,
    private val chatRepository: ChatRepository,
    private val teamMemberRepository: TeamMemberRepository,
    private val logger: Logger
) : MessageService {

    override fun create(chatId: ChatId, author: TeamMemberId, content: String): Message {
        chatRepository.get(chatId) ?: throw NotFoundException("Chat does not exist")

        val message = Message(
            author = teamMemberRepository.get(author) ?: throw NotFoundException("Sender does not exist"),
            chatId = chatId,
            message = content
        )

        return messageRepository.create(message)
    }
}