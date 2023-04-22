package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.responses

import io.swagger.v3.oas.annotations.media.Schema
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.ChatStatus

@Schema(name = "Status")
data class Status(
    val status: ChatStatus,
    val name: String,
    val description: String,
)
