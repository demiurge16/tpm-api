package net.nuclearprometheus.tpm.applicationserver.domain.queries.search

fun <TEntity : Any> nonFiltered() = Search<TEntity>(emptyList())

fun <TEntity : Any> search(block: SearchBuilder<TEntity>.() -> Unit): Search<TEntity> {
    val builder = SearchBuilder<TEntity>()
    builder.block()
    return builder.build()
}

class SearchBuilder<TEntity : Any> {
    private val operationStack = mutableListOf<Operation<TEntity>>()

    fun not(block: SearchBuilder<TEntity>.() -> Unit) {
        block()
        operationStack.add(Operation.Not())
    }

    fun and(block: SearchBuilder<TEntity>.() -> Unit) {
        block()
        operationStack.add(Operation.And())
    }

    fun or(block: SearchBuilder<TEntity>.() -> Unit) {
        block()
        operationStack.add(Operation.Or())
    }

    fun equals(field: String, value: Any) {
        operationStack.add(Operation.Equals(field, value))
    }

    fun contains(field: String, value: String) {
        operationStack.add(Operation.Contains(field, value))
    }

    fun greaterThan(field: String, value: String) {
        operationStack.add(Operation.GreaterThan(field, value))
    }

    fun lessThan(field: String, value: String) {
        operationStack.add(Operation.LessThan(field, value))
    }

    fun greaterThanOrEqual(field: String, value: String) {
        operationStack.add(Operation.GreaterThanOrEqual(field, value))
    }

    fun lessThanOrEqual(field: String, value: String) {
        operationStack.add(Operation.LessThanOrEqual(field, value))
    }

    fun all(field: String, values: List<String>) {
        operationStack.add(Operation.AllElements(field, values))
    }

    fun any(field: String, values: List<String>) {
        operationStack.add(Operation.AnyElement(field, values))
    }

    fun none(field: String, values: List<String>) {
        operationStack.add(Operation.NoneElement(field, values))
    }

    fun isNull(field: String) {
        operationStack.add(Operation.IsNull(field))
    }

    fun isEmpty(field: String) {
        operationStack.add(Operation.IsEmpty(field))
    }

    fun build(): Search<TEntity> {
        return Search(operationStack)
    }
}