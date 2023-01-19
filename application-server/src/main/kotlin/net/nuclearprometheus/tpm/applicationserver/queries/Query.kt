package net.nuclearprometheus.tpm.applicationserver.queries

import net.nuclearprometheus.tpm.applicationserver.queries.operations.OperationType
import net.nuclearprometheus.tpm.applicationserver.queries.operations.binary.*
import net.nuclearprometheus.tpm.applicationserver.queries.operations.unary.EmptyComparison
import net.nuclearprometheus.tpm.applicationserver.queries.operations.unary.NullComparison

class Query<T : Any>(
    private val operationStack: List<Token>
) {

    fun evaluate(entity: T): Boolean {
        if (operationStack.isEmpty()) return true

        val stack = mutableListOf<Boolean>()
        operationStack.forEach { token ->
            when {
                token.isOperation() -> stack.add(token.evaluate(entity))
                token.isAnd() -> stack.add(stack.removeLast() && stack.removeLast())
                token.isOr() -> stack.add(stack.removeLast() || stack.removeLast())
                token.isNot() -> stack.add(!stack.removeLast())
            }
        }

        return stack.removeLast()
    }

    override fun toString(): String {
        return operationStack.joinToString(", ")
    }

    private fun Token.isOperation(): Boolean = this.type == TokenType.COMPARISON
    private fun Token.isAnd(): Boolean = this.type == TokenType.AND
    private fun Token.isOr(): Boolean = this.type == TokenType.OR
    private fun Token.isNot(): Boolean = this.type == TokenType.NOT

    private fun Token.evaluate(entity: T): Boolean {
        val tokens = this.value.split(":")

        val field = tokens.get(0)
        val operator = tokens.get(1)
        val value = tokens.getOrNull(2)

        return when (operator.toOperationType()) {
            OperationType.EQUALS -> EqualsComparison<T>(field, value!!).evaluate(entity)
            OperationType.CONTAINS -> ContainsComparison<T>(field, value!!).evaluate(entity)
            OperationType.GREATER_THAN -> GreaterThanComparison<T>(field, value!!).evaluate(entity)
            OperationType.LESS_THAN -> LessThanComparison<T>(field, value!!).evaluate(entity)
            OperationType.GREATER_THAN_OR_EQUAL -> GreaterThanOrEqualComparison<T>(field, value!!).evaluate(entity)
            OperationType.LESS_THAN_OR_EQUAL -> LessThanOrEqualComparison<T>(field, value!!).evaluate(entity)
            OperationType.ANY -> AnyComparison<T>(field, value!!.toList()).evaluate(entity)
            OperationType.ALL -> AllComparison<T>(field, value!!.toList()).evaluate(entity)
            OperationType.IS_NULL -> NullComparison<T>(field).evaluate(entity)
            OperationType.IS_EMPTY -> EmptyComparison<T>(field).evaluate(entity)
        }
    }

    private fun String.toOperationType() = OperationType.values().find { it.symbol == this }
        ?: throw IllegalArgumentException("Unknown operation type: $this")

    private fun String.toList() = this.split(",").map { it.trim() }
}
