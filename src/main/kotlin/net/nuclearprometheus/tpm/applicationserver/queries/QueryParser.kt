package net.nuclearprometheus.tpm.applicationserver.queries

import net.nuclearprometheus.tpm.applicationserver.queries.operations.Operation
import net.nuclearprometheus.tpm.applicationserver.queries.operations.OperationType
import net.nuclearprometheus.tpm.applicationserver.queries.operations.binary.*
import net.nuclearprometheus.tpm.applicationserver.queries.operations.logical.AndOperation
import net.nuclearprometheus.tpm.applicationserver.queries.operations.logical.NotOperation
import net.nuclearprometheus.tpm.applicationserver.queries.operations.logical.OrOperation
import net.nuclearprometheus.tpm.applicationserver.queries.operations.unary.EmptyComparison
import net.nuclearprometheus.tpm.applicationserver.queries.operations.unary.NullComparison

/**
 * This function parses a query string into a [Query] object.
 *
 * Query string is a custom query language that is used to filter data
 * It is a combination of logical operators and comparisons
 *
 * Logical operators are:
 * & - and
 * | - or
 * ! - not
 *
 * Comparisons are in the form of:
 * fieldname:comparison:value
 * Where fieldname is the name of the field to compare
 * comparison is one of the following:
 * eq - equals
 * contains - contains
 * gt - greater than
 * lt - less than
 * gte - greater than or equal
 * lte - less than or equal
 * any - any
 * all - all
 * none - none
 * null - is null
 * empty - is empty
 *
 * value is the value to compare to
 *
 * Possible value types are:
 * String - "value"
 * Int - 1
 * Double - 1.0
 * Boolean - true
 * List - ("value1","value2")
 *
 * Nested queries are supported
 *
 * Example:
 * !(name:eq:"tom" | name:eq:"jerry")
 * & middlename:null
 * & lastname:eq:"smith"
 * | (age:gt:18 & age:lt:30)
 * & occupations:in:"programmer","developer"
 * & countries:in:"USA","UK"
 *
 * @param query The query string to parse
 * @return The parsed query object
 */
fun <TEntity : Any> createQuery(query: String): Query<TEntity> {
    val tokens = query.sanitize().tokenize()

    val priorities = mapOf(
        TokenType.OPEN_PARENTHESIS to 0,
        TokenType.CLOSE_PARENTHESIS to 0,
        TokenType.NOT to 1,
        TokenType.AND to 1,
        TokenType.OR to 1,
        TokenType.COMPARISON to 2,
    )

    val stack = mutableListOf<Token>()
    val output = mutableListOf<Token>()

    tokens.forEach { token ->
        when (token.type) {
            TokenType.OPEN_PARENTHESIS -> stack.add(token.asOpenParenthesisToken())
            TokenType.CLOSE_PARENTHESIS -> {
                while (stack.isNotEmpty() && stack.last().type != TokenType.OPEN_PARENTHESIS) {
                    output.add(stack.removeLast())
                }
                stack.removeLast()
            }
            TokenType.COMPARISON -> output.add(token.asComparisonToken())
            TokenType.NOT, TokenType.AND, TokenType.OR -> {
                while (stack.isNotEmpty() && priorities[stack.last().type]!! >= priorities[token.type]!!) {
                    output.add(stack.removeLast())
                }
                stack.add(token.asLogicalToken())
            }
        }
    }

    while (stack.isNotEmpty()) {
        output.add(stack.removeLast())
    }

    val resultStack = output.map {
        when (it.type) {
            TokenType.COMPARISON -> it.toOperator<TEntity>()
            TokenType.NOT -> NotOperation()
            TokenType.AND -> AndOperation()
            TokenType.OR -> OrOperation()
            else -> throw IllegalArgumentException("Invalid token type: ${it.type}")
        }
    }

    return Query(resultStack)
}

private fun <TEntity : Any> Token.toOperator(): Operation<TEntity> {
    val tokens = this.value.split(":")

    val field = tokens.get(0)
    val operator = tokens.get(1)
    val value = tokens.getOrNull(2)

    return when (operator.toOperationType()) {
        OperationType.EQUALS -> EqualsComparison<TEntity>(field, value!!)
        OperationType.CONTAINS -> ContainsComparison<TEntity>(field, value!!)
        OperationType.GREATER_THAN -> GreaterThanComparison<TEntity>(field, value!!)
        OperationType.LESS_THAN -> LessThanComparison<TEntity>(field, value!!)
        OperationType.GREATER_THAN_OR_EQUAL -> GreaterThanOrEqualComparison<TEntity>(field, value!!)
        OperationType.LESS_THAN_OR_EQUAL -> LessThanOrEqualComparison<TEntity>(field, value!!)
        OperationType.ANY -> AnyComparison<TEntity>(field, value!!.toList())
        OperationType.ALL -> AllComparison<TEntity>(field, value!!.toList())
        OperationType.NONE -> NoneComparison<TEntity>(field, value!!.toList())
        OperationType.IS_NULL -> NullComparison<TEntity>(field)
        OperationType.IS_EMPTY -> EmptyComparison<TEntity>(field)
    }
}

private fun String.toOperationType() = OperationType.values().find { it.symbol == this }
    ?: throw IllegalArgumentException("Unknown operation type: $this")

private fun String.toList() = this.split(",").map { it.trim() }

private fun String.sanitize() = this.replace(" ", "")
    .replace("\t", "")
    .replace("\r", "")
    .replace("\n", "")

private fun String.tokenize() = this.replace(TokenType.OPEN_PARENTHESIS.symbol, " ( ")
    .replace(TokenType.CLOSE_PARENTHESIS.symbol, " ) ")
    .replace(TokenType.NOT.symbol, " ! ")
    .replace(TokenType.AND.symbol, " & ")
    .replace(TokenType.OR.symbol, " | ")
    .split(" ")
    .filter { it.isNotBlank() }

private fun String.asOpenParenthesisToken() = Token(TokenType.OPEN_PARENTHESIS, this)
private fun String.asComparisonToken() = Token(TokenType.COMPARISON, this)
private fun String.asLogicalToken() = Token(TokenType.fromSymbol(this), this)
private val String.type: TokenType; get() = TokenType.fromSymbol(this)
