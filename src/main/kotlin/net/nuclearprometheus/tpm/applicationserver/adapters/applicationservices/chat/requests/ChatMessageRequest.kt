package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.requests

sealed class ChatMessageRequest {
    data class Create(val message: String)
}