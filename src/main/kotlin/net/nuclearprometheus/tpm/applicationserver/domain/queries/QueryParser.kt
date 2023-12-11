package net.nuclearprometheus.tpm.applicationserver.domain.queries

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.Specification
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.tokenizer.Tokenizer.tokenize

object QueryParser {

    /**
     * This function parses a query string into a [Specification] object.
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
    fun <TEntity : Any> createSearch(query: String, specificationBuilder: SpecificationBuilder<TEntity>): Specification<TEntity> {
        val tokens = query.tokenize()

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

        while (stack.isNotEmpty()) {
            output.add(stack.removeLast())
        }

        return output.fold(mutableListOf<Specification<TEntity>>()) { outputStack, token ->
            when (token) {
                is Token.Comparison -> {
                    val operation = specificationBuilder.createSpecification(token.field, token.operator, token.value)
                    outputStack.add(operation)
                }

                is Token.Not -> {
                    val operation = outputStack.removeLast()
                    outputStack.add(Specification.NotSpecification(operation))
                }

                is Token.And -> {
                    val right = outputStack.removeLast()
                    val left = outputStack.removeLast()

                    outputStack.add(Specification.AndSpecification(left, right))
                }

                is Token.Or -> {
                    val right = outputStack.removeLast()
                    val left = outputStack.removeLast()

                    outputStack.add(Specification.OrSpecification(left, right))
                }

                else -> throw IllegalArgumentException("Invalid token: $token")
            }
            outputStack
        }.removeLast()
    }
}
