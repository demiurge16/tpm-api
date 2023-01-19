package net.nuclearprometheus.tpm.applicationserver.queries.operations.unary

class NullComparison<TEntity : Any>(override val field: String) : UnaryComparisonOperation<TEntity> {

    override fun evaluate(entity: TEntity) = getFieldValue(entity) == null
}
