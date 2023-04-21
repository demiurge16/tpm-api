package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.responses

import io.swagger.v3.oas.annotations.media.Schema
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.Pageable
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.PageableImpl
import java.util.*

sealed class ChatMessageResponse {

    @Schema(name = "ChatMessageResponse.Page")
    data class Page(
        override val items: List<View>,
        override val totalPages: Int,
        override val totalElements: Int
    ) : ChatMessageResponse(), Pageable<View> by PageableImpl(items, totalPages, totalElements)

    @Schema(name = "ChatMessageResponse.View")
    data class View(val id: UUID, val author: AuthorView, val message: String) {

        @Schema(name = "ChatMessageResponse.View.AuthorView")
        data class AuthorView(
            val teamMemberId: UUID,
            val userId: UUID,
            val firstName: String,
            val lastName: String,
            val email: String,
        )
    }
}