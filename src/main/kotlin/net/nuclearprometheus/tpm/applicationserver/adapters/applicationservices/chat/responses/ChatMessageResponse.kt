package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.responses

import java.util.*

sealed class ChatMessageResponse {
    data class View(val id: UUID, val author: AuthorView, val message: String) {

        data class AuthorView(
            val teamMemberId: UUID,
            val userId: UUID,
            val firstName: String,
            val lastName: String,
            val email: String,
        )
    }
}