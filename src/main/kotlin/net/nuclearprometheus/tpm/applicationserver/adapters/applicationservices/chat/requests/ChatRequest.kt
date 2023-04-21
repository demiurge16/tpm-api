package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.requests

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

sealed class ChatRequest {

    @Schema(name = "ChatRequest.Update")
    data class Update(
        val title: String,
        val description: String,
    ) : ChatRequest()

    @Schema(name = "ChatRequest.TransferOwnership")
    data class TransferOwnership(
        val newOwnerId: UUID,
    ) : ChatRequest()

    @Schema(name = "ChatRequest.AddParticipant")
    data class AddParticipant(
        val participantId: UUID,
    ) : ChatRequest()

    @Schema(name = "ChatRequest.RemoveParticipant")
    data class RemoveParticipant(
        val participantId: UUID,
    ) : ChatRequest()
}