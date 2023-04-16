package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.responses

import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.ChatStatus
import java.util.*

sealed class ChatResponse {

    data class View(
        val id: UUID,
        val title: String,
        val description: String,
        val status: ChatStatusView,
        val owner: ChatMember,
        val participants: List<ChatMember>,
        val projectId: UUID
    ) : ChatResponse() {

        data class ChatStatusView(
            val status: ChatStatus,
            val name: String,
            val description: String,
        )

        data class ChatMember(
            val teamMemberId: UUID,
            val userId: UUID,
            val firstName: String,
            val lastName: String,
            val email: String,
        )
    }

    data class ChatStatusView(
        val id: UUID,
        val status: ChatStatus,
        val name: String,
        val description: String,
    ) : ChatResponse()

    data class ChatMember(
        val id: UUID,
        val teamMemberId: UUID,
        val userId: UUID,
        val firstName: String,
        val lastName: String,
        val email: String,
    ) : ChatResponse()
}