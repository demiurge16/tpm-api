package net.nuclearprometheus.tpm.applicationserver.queries

data class Token(
    val type: TokenType,
    val value: String,
)
