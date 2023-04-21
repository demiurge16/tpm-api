package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.requests

import io.swagger.v3.oas.annotations.media.Schema

sealed class ChatMessageRequest {

    @Schema(description = "Create a new message in a chat")
    data class Create(val message: String)
}