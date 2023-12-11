package net.nuclearprometheus.tpm.applicationserver.domain.queries

enum class TokenType(val symbol: String) {
    OPEN_PARENTHESIS("("),
    CLOSE_PARENTHESIS(")"),
    NOT("!"),
    AND("&"),
    OR("|"),
    COMPARISON("");
}
