package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat

import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.singlePage
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.mappers.ChatMessageMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.requests.ChatMessageRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.ChatId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.chat.ChatRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.chat.MessageRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.chat.MessageService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.util.*

@Service
class ChatMessageApplicationService(
    private val repository: MessageRepository,
    private val service: MessageService,
    private val chatRepository: ChatRepository,
    private val userContextProvider: UserContextProvider
) {

    private val logger = loggerFor(ChatMessageApplicationService::class.java)

    fun getMessages(chatId: UUID) = with(logger) {
        info("getMessages($chatId)")

        singlePage(repository.getAllByChatId(ChatId(chatId))).map { it.toView() }
    }

    fun createMessage(chatId: UUID, request: ChatMessageRequest.Create) = with(logger) {
        info("createMessage($chatId, $request)")

        val currentUser = userContextProvider.getCurrentUser()
        val chatMember = chatRepository.get(ChatId(chatId))?.let{
            it.participants.find { it.user.id == currentUser.id }
        } ?: throw IllegalArgumentException("Chat not found or user is not a member of the chat")

        service.create(ChatId(chatId), chatMember.id, request.message).toView()
    }
}