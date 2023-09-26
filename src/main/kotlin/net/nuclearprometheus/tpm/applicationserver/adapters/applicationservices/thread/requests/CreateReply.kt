package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.requests

import java.util.*

data class CreateReply(val content: String, val parentReplyId: UUID?)
