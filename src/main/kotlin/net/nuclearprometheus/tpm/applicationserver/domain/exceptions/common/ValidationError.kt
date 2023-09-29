package net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common

data class ValidationError(
    val field: String,
    val message: String
)