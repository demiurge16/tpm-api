package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat

import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.singlePage
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.mappers.ChatMapper.toChatStatus
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.mappers.ChatMapper.toNewOwner
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.mappers.ChatMapper.toNewParticipant
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.mappers.ChatMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.requests.ChatRequest
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.ChatId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.chat.ChatRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.chat.ChatService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.util.*

@Service
class ChatApplicationService(private val repository: ChatRepository, private val service: ChatService) {

    private val logger = loggerFor(ChatApplicationService::class.java)

    fun getChats() = with(logger) {
        info("getChats()")

        singlePage(repository.getAll()).map { it.toView() }
    }

    fun getChat(chatId: UUID) = with(logger) {
        info("getChat($chatId)")

        repository.get(ChatId(chatId))?.toView() ?: throw NotFoundException("Chat with id $chatId not found")
    }

    fun updateChat(chatId: UUID, request: ChatRequest.Update) = with(logger) {
        info("updateChat($chatId, $request)")

        service.update(
            id = ChatId(chatId),
            title = request.title,
            description = request.description,
        ).toView()
    }

    fun activateChat(chatId: UUID) = with(logger) {
        info("activateChat($chatId)")

        service.activate(ChatId(chatId)).toChatStatus()
    }

    fun freezeChat(chatId: UUID) = with(logger) {
        info("freezeChat($chatId)")

        service.freeze(ChatId(chatId)).toChatStatus()
    }

    fun archiveChat(chatId: UUID) = with(logger) {
        info("archiveChat($chatId)")

        service.archive(ChatId(chatId)).toChatStatus()
    }

    fun transferOwnership(chatId: UUID, request: ChatRequest.TransferOwnership) = with(logger) {
        info("transferOwnership($chatId, $request)")

        service.transferOwnership(ChatId(chatId), TeamMemberId(request.newOwnerId)).toNewOwner()
    }

    fun addParticipant(chatId: UUID, request: ChatRequest.AddParticipant) = with(logger) {
        info("addParticipant($chatId, $request)")

        service.addParticipant(ChatId(chatId), TeamMemberId(request.participantId)).toNewParticipant()
    }

    fun removeParticipant(chatId: UUID, request: ChatRequest.RemoveParticipant) {
        with(logger) {
            info("removeParticipant($chatId, $request)")

            service.removeParticipant(ChatId(chatId), TeamMemberId(request.participantId))
        }
    }
}