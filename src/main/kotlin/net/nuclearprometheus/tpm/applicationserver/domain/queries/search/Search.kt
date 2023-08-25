package net.nuclearprometheus.tpm.applicationserver.domain.queries.search

class Search<TEntity : Any>(val operationStack: List<Operation<TEntity>>) {
    override fun toString(): String {
        return operationStack.joinToString(", ")
    }
}