package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.tokenizer


import net.nuclearprometheus.tpm.applicationserver.domain.queries.Token
import net.nuclearprometheus.tpm.applicationserver.domain.queries.TokenType
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.Operator

object Tokenizer {
    fun String.tokenize() = this.replace(" ", "")
        .replace("\t", "")
        .replace("\r", "")
        .replace("\n", "")
        .replace(TokenType.OPEN_PARENTHESIS.symbol, " ( ")
        .replace(TokenType.CLOSE_PARENTHESIS.symbol, " ) ")
        .replace(TokenType.NOT.symbol, " ! ")
        .replace(TokenType.AND.symbol, " & ")
        .replace(TokenType.OR.symbol, " | ")
        .split(" ")
        .filter { it.isNotBlank() }
        .map { it.toToken() }

    private fun String.toToken(): Token {
        return when (this) {
            TokenType.OPEN_PARENTHESIS.symbol -> Token.OpenParenthesis()
            TokenType.CLOSE_PARENTHESIS.symbol -> Token.CloseParenthesis()
            TokenType.NOT.symbol -> Token.Not()
            TokenType.AND.symbol -> Token.And()
            TokenType.OR.symbol -> Token.Or()
            else -> {
                val tokens = this.split(":")
                val field = tokens.get(0)
                val operator = tokens.get(1)
                val value = tokens.getOrNull(2)

                Token.Comparison(field, operator.toOperator(), value)
            }
        }
    }

    private fun String.toOperator(): Operator {
        return when (this) {
            Operator.EQUALS.symbol -> Operator.EQUALS
            Operator.CONTAINS.symbol -> Operator.CONTAINS
            Operator.GREATER_THAN.symbol -> Operator.GREATER_THAN
            Operator.GREATER_THAN_OR_EQUAL.symbol -> Operator.GREATER_THAN_OR_EQUAL
            Operator.LESS_THAN.symbol -> Operator.LESS_THAN
            Operator.LESS_THAN_OR_EQUAL.symbol -> Operator.LESS_THAN_OR_EQUAL
            Operator.ALL.symbol -> Operator.ALL
            Operator.ANY.symbol -> Operator.ANY
            Operator.NONE.symbol -> Operator.NONE
            Operator.IS_NULL.symbol -> Operator.IS_NULL
            Operator.IS_EMPTY.symbol -> Operator.IS_EMPTY
            else -> throw IllegalArgumentException("Invalid operator: $this")
        }
    }
}