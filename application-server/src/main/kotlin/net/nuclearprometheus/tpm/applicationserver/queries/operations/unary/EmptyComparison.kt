package net.nuclearprometheus.tpm.applicationserver.queries.operations.unary

class EmptyComparison<TEntity : Any>(override val field: String) : UnaryComparisonOperation<TEntity> {

    override fun evaluate(entity: TEntity) =
        when (val fieldValue = getFieldValue(entity)) {
            is String -> fieldValue.isEmpty()
            is Collection<*> -> fieldValue.isEmpty()
            else -> throw IllegalArgumentException("Operation <contains> is not supported for field type: ${fieldValue!!::class}")
        }
}
