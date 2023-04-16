package net.nuclearprometheus.tpm.applicationserver.queries.operations.unary

class EmptyComparison<TEntity : Any>(override val field: String) : UnaryComparisonOperation<TEntity> {

    override fun evaluate(entity: TEntity) =
        when (val fieldValue = getFieldValue(entity)) {
            is String -> fieldValue.isEmpty()
            is Collection<*> -> fieldValue.isEmpty()
            else -> throw IllegalArgumentException("Operation <contains> is not supported for field type: ${fieldValue!!::class}")
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EmptyComparison<*>

        if (field != other.field) return false

        return true
    }

    override fun hashCode() = field.hashCode()
}
