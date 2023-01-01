package net.nuclearprometheus.translationprojectmanager.queries.operations

interface ComparisonOperation<TEntity : Any> : Operation {
    val field: String

    fun evaluate(entity: TEntity): Boolean
}
