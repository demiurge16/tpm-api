package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.requests

import java.util.UUID

sealed class ReplyRequest {
    data class Create(val content: String, val parentReplyId: UUID?) : ReplyRequest()

    data class Update(val content: String) : ReplyRequest()
}