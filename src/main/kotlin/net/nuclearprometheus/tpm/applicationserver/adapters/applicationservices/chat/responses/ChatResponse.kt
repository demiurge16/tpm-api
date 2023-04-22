package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.responses

import io.swagger.v3.oas.annotations.media.Schema
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.Pageable
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.PageableImpl
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.ChatStatus
import java.util.*

sealed class ChatResponse {

    @Schema(name = "ChatResponse.Page")
    data class Page(
        override val items: List<Chat>,
        override val totalPages: Int,
        override val totalElements: Int,
    ) : ChatResponse(), Pageable<Chat> by PageableImpl(items, totalPages, totalElements)

    @Schema(name = "ChatResponse.Chat")
    data class Chat(
        val id: UUID,
        val title: String,
        val description: String,
        val status: Status,
        val owner: Member,
        val participants: List<Member>,
        val projectId: UUID
    ) : ChatResponse()

    @Schema(name = "ChatResponse.ChatMember")
    data class ChatMember(
        val id: UUID,
        val member: Member
    ) : ChatResponse()

    @Schema(name = "ChatResponse.ChatStatusView")
    data class ChatStatus(
        val id: UUID,
        val status: Status
    ) : ChatResponse()
}
