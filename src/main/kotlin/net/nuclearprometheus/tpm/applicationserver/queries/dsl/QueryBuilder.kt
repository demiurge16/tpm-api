package net.nuclearprometheus.tpm.applicationserver.queries.dsl

import net.nuclearprometheus.tpm.applicationserver.queries.Query
import net.nuclearprometheus.tpm.applicationserver.queries.operations.Operation
import net.nuclearprometheus.tpm.applicationserver.queries.operations.binary.*
import net.nuclearprometheus.tpm.applicationserver.queries.operations.logical.AndOperation
import net.nuclearprometheus.tpm.applicationserver.queries.operations.logical.NotOperation
import net.nuclearprometheus.tpm.applicationserver.queries.operations.logical.OrOperation
import net.nuclearprometheus.tpm.applicationserver.queries.operations.unary.NullComparison

fun <TEntity : Any> query(block: QueryBuilder<TEntity>.() -> Unit): Query<TEntity> {
    val builder = QueryBuilder<TEntity>()
    builder.block()
    return builder.build()
}

class QueryBuilder<TEntity : Any> {
    private val operationStack = mutableListOf<Operation<TEntity>>()

    fun not(block: QueryBuilder<TEntity>.() -> Unit) {
        block()
        operationStack.add(NotOperation())
    }

    fun and(block: QueryBuilder<TEntity>.() -> Unit) {
        block()
        operationStack.add(AndOperation())
    }

    fun or(block: QueryBuilder<TEntity>.() -> Unit) {
        block()
        operationStack.add(OrOperation())
    }

    fun equals(field: String, value: String) {
        operationStack.add(EqualsComparison(field, value))
    }

    fun contains(field: String, value: String) {
        operationStack.add(ContainsComparison(field, value))
    }

    fun greaterThan(field: String, value: String) {
        operationStack.add(GreaterThanComparison(field, value))
    }

    fun lessThan(field: String, value: String) {
        operationStack.add(LessThanComparison(field, value))
    }

    fun isNull(field: String) {
        operationStack.add(NullComparison(field))
    }

    fun all(field: String, values: List<String>) {
        operationStack.add(AllComparison(field, values))
    }

    fun any(field: String, values: List<String>) {
        operationStack.add(AnyComparison(field, values))
    }

    fun build(): Query<TEntity> {
        return Query(operationStack)
    }
}