package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.requests

import java.util.*

sealed class ChatRequest {

    data class Update(
        val title: String,
        val description: String,
    ) : ChatRequest()

    data class TransferOwnership(
        val newOwnerId: UUID,
    ) : ChatRequest()

    data class AddParticipant(
        val participantId: UUID,
    ) : ChatRequest()

    data class RemoveParticipant(
        val participantId: UUID,
    ) : ChatRequest()
}