package net.nuclearprometheus.translationprojectmanager.queries

enum class TokenType(val symbol: String) {
    OPEN_PARENTHESIS("("),
    CLOSE_PARENTHESIS(")"),
    NOT("!"),
    AND("&"),
    OR("|"),
    COMPARISON("");

    companion object {
        fun fromSymbol(symbol: String) = values().find { it.symbol == symbol } ?: COMPARISON
    }
}
