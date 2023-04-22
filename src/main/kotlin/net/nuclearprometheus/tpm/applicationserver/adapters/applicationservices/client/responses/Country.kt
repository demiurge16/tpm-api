package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Country")
data class Country(
    val code: String,
    val name: String,
)