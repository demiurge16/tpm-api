package net.nuclearprometheus.tpm.applicationserver.queries

import net.nuclearprometheus.tpm.applicationserver.queries.operations.*
import net.nuclearprometheus.tpm.applicationserver.queries.operations.binary.*
import net.nuclearprometheus.tpm.applicationserver.queries.operations.logical.AndOperation
import net.nuclearprometheus.tpm.applicationserver.queries.operations.logical.NotOperation
import net.nuclearprometheus.tpm.applicationserver.queries.operations.logical.OrOperation
import net.nuclearprometheus.tpm.applicationserver.queries.operations.unary.EmptyComparison
import net.nuclearprometheus.tpm.applicationserver.queries.operations.unary.NullComparison

class Query<T : Any>(
    internal val operationStack: List<Operation<T>>
) {
    fun evaluate(entity: T): Boolean {
        if (operationStack.isEmpty()) return true

        val stack = mutableListOf<Boolean>()
        operationStack.forEach { operation ->
            when (operation) {
                is ComparisonOperation<*> -> stack.add((operation as ComparisonOperation<T>).evaluate(entity))
                is AndOperation -> stack.add(stack.removeLast() && stack.removeLast())
                is OrOperation -> stack.add(stack.removeLast() || stack.removeLast())
                is NotOperation -> stack.add(!stack.removeLast())
            }
        }

        return stack.removeLast()
    }

    override fun toString(): String {
        return operationStack.joinToString(", ")
    }
}
