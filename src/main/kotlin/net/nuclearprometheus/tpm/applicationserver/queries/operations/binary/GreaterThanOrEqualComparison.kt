package net.nuclearprometheus.tpm.applicationserver.queries.operations.binary

class GreaterThanOrEqualComparison<TEntity : Any>(override val field: String, override val value: String) : BinaryComparisonOperation<TEntity, String> {

    override fun evaluate(entity: TEntity) =
        when (val fieldValue = getFieldValue(entity)) {
            is String -> fieldValue >= value
            is Number -> fieldValue.toDouble() >= value.toDouble()
            else -> throw IllegalArgumentException("Operation <gte> is not supported for field type: ${fieldValue!!::class}")
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GreaterThanOrEqualComparison<*>

        if (field != other.field) return false
        if (value != other.value) return false

        return true
    }

    override fun hashCode() = 31 * field.hashCode() + value.hashCode()
}
