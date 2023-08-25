package net.nuclearprometheus.tpm.applicationserver.domain.queries

data class Token(
    val type: TokenType,
    val value: String,
)
