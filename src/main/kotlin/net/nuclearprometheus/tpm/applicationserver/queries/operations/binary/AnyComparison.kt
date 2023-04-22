package net.nuclearprometheus.tpm.applicationserver.queries.operations.binary

class AnyComparison<TEntity : Any>(override val field: String, override val value: List<String>)
    : BinaryComparisonOperation<TEntity, List<String>> {

    override fun evaluate(entity: TEntity) =
        getFieldValue(entity)?.let { fieldValue ->
            when {
                fieldValue is String -> fieldValue in value
                fieldValue::class.java.isEnum -> fieldValue.toString() in value.map { it.removeSurrounding("\"") }.toSet()
                fieldValue is Collection<*> -> fieldValue.map { it.toString() }
                    .intersect(value.map { it.removeSurrounding("\"") }.toSet())
                    .isNotEmpty()
                else -> fieldValue.toString() in value
            }
        } ?: false

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AnyComparison<*>

        if (field != other.field) return false
        if (value != other.value) return false

        return true
    }

    override fun hashCode() = 31 * field.hashCode() + value.hashCode()
}
