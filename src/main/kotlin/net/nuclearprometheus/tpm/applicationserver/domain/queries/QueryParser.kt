package net.nuclearprometheus.tpm.applicationserver.domain.queries

import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.Search
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.dsl.SearchSpecification
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.operations.*
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.tokenizer.Tokenizer.tokenize

/**
 * This function parses a query string into a [Search] object.
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
 * Constant value - CONSTANT_VALUE
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
 * & (hobbies:all:"football","basketball" | hobbies:none:"tennis","golf")
 * & mood:eq:HAPPY
 *
 * @param expression The query string to parse
 * @param specification The specification to use for parsing
 * @param TEntity The type of the entity to filter
 * @return The parsed query object
 */
fun <TEntity : Any> createSearch(expression: String, specification: SearchSpecification<TEntity>): Search<TEntity> {
    val tokens = expression.tokenize()

    val priorities = mapOf(
        Token.OpenParenthesis::class to 0,
        Token.CloseParenthesis::class to 0,
        Token.Not::class to 1,
        Token.And::class to 1,
        Token.Or::class to 1,
        Token.Comparison::class to 2,
    )

    val stack = mutableListOf<Token>()
    val output = mutableListOf<Token>()

    tokens.forEach { token ->
        when (token) {
            is Token.OpenParenthesis -> stack.add(token)
            is Token.CloseParenthesis -> {
                while (stack.isNotEmpty() && stack.last() !is Token.OpenParenthesis) {
                    output.add(stack.removeLast())
                }
                stack.removeLast()
            }
            is Token.Comparison -> output.add(token)
            is Token.Not, is Token.And, is Token.Or -> {
                while (stack.isNotEmpty() && priorities[stack.last()::class]!! >= priorities[token::class]!!) {
                    output.add(stack.removeLast())
                }
                stack.add(token)
            }
        }
    }

    val root = output.fold(mutableListOf<OperationNode<TEntity>>()) { outputStack, token ->
        when (token) {
            is Token.Comparison -> {
                val operation = specification.createOperation(token.field, token.operator, token.value)
                outputStack.add(operation)
            }
            is Token.Not -> {
                val operation = outputStack.removeLast()
                outputStack.add(Not(operation))
            }
            is Token.And -> {
                val right = outputStack.removeLast()
                val left = outputStack.removeLast()

                outputStack.add(And(left, right))
            }
            is Token.Or -> {
                val right = outputStack.removeLast()
                val left = outputStack.removeLast()

                outputStack.add(Or(left, right))
            }
            else -> throw IllegalArgumentException("Invalid token: $token")
        }
        outputStack
    }.removeLast()

    return Search(root)
}
