package net.nuclearprometheus.tpm.applicationserver.queries.operations.binary

class EqualsComparison<TEntity : Any>(override val field: String, override val value: String)
    : BinaryComparisonOperation<TEntity, String> {

    override fun evaluate(entity: TEntity) = getFieldValue(entity) == value

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EqualsComparison<*>

        if (field != other.field) return false
        if (value != other.value) return false

        return true
    }

    override fun hashCode() = 31 * field.hashCode() + value.hashCode()
}
