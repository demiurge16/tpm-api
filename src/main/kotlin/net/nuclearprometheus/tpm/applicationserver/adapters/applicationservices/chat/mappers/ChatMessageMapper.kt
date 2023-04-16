package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.responses.ChatMessageResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.Message

object ChatMessageMapper {
    fun Message.toView() = ChatMessageResponse.View(
        id = id.value,
        author = ChatMessageResponse.View.AuthorView(
            teamMemberId = author.id.value,
            userId = author.user.id.value,
            firstName = author.user.firstName,
            lastName = author.user.lastName,
            email = author.user.email,
        ),
        message = message
    )
}