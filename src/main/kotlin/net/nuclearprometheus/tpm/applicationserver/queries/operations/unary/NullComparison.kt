package net.nuclearprometheus.tpm.applicationserver.queries.operations.unary

class NullComparison<TEntity : Any>(override val field: String) : UnaryComparisonOperation<TEntity> {

    override fun evaluate(entity: TEntity) = getFieldValue(entity) == null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NullComparison<*>

        if (field != other.field) return false

        return true
    }

    override fun hashCode() = field.hashCode()
}
