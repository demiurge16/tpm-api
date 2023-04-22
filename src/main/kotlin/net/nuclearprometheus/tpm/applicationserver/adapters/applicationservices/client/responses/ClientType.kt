package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(name = "ClientType")
data class ClientType(
    val id: UUID,
    val name: String,
    val description: String,
    val corporate: Boolean,
)
