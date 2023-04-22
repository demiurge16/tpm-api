package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.responses

import io.swagger.v3.oas.annotations.media.Schema
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.Pageable
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.PageableImpl
import java.time.ZonedDateTime
import java.util.*

sealed class ChatMessageResponse {

    @Schema(name = "ChatMessageResponse.Page")
    data class Page(
        override val items: List<Message>,
        override val totalPages: Int,
        override val totalElements: Int
    ) : ChatMessageResponse(), Pageable<Message> by PageableImpl(items, totalPages, totalElements)

    @Schema(name = "ChatMessageResponse.View")
    data class Message(val id: UUID, val author: Author, val message: String, val timestamp: ZonedDateTime) : ChatMessageResponse()
}
