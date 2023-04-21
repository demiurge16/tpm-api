package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.responses

import io.swagger.v3.oas.annotations.media.Schema
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.Pageable
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.PageableImpl
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.ChatStatus
import java.util.*

sealed class ChatResponse {

    @Schema(name = "ChatResponse.Page")
    data class Page(
        override val items: List<View>,
        override val totalPages: Int,
        override val totalElements: Int,
    ) : ChatResponse(), Pageable<View> by PageableImpl(items, totalPages, totalElements)

    @Schema(name = "ChatResponse.View")
    data class View(
        val id: UUID,
        val title: String,
        val description: String,
        val status: ChatStatusView,
        val owner: ChatMember,
        val participants: List<ChatMember>,
        val projectId: UUID
    ) : ChatResponse() {

        @Schema(name = "ChatResponse.View.ChatStatusView")
        data class ChatStatusView(
            val status: ChatStatus,
            val name: String,
            val description: String,
        )

        @Schema(name = "ChatResponse.View.ChatMember")
        data class ChatMember(
            val teamMemberId: UUID,
            val userId: UUID,
            val firstName: String,
            val lastName: String,
            val email: String,
        )
    }

    @Schema(name = "ChatResponse.ChatStatusView")
    data class ChatStatusView(
        val id: UUID,
        val status: ChatStatus,
        val name: String,
        val description: String,
    ) : ChatResponse()

    @Schema(name = "ChatResponse.ChatMember")
    data class ChatMember(
        val id: UUID,
        val teamMemberId: UUID,
        val userId: UUID,
        val firstName: String,
        val lastName: String,
        val email: String,
    ) : ChatResponse()
}