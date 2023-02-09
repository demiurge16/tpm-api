package net.nuclearprometheus.tpm.applicationserver.queries.operations.binary

class AnyComparison<TEntity : Any>(override val field: String, override val value: List<String>)
    : BinaryComparisonOperation<TEntity, List<String>> {

    override fun evaluate(entity: TEntity) =
        getFieldValue(entity)?.let { fieldValue ->
            when {
                fieldValue is String -> fieldValue in value
                fieldValue::class.java.isEnum -> value.map { it.removeSurrounding("\"") }.toSet()
                    .contains(fieldValue.toString())
                fieldValue is Collection<*> -> fieldValue.map { it.toString() }
                    .intersect(value.map { it.removeSurrounding("\"") }.toSet())
                    .isNotEmpty()
                else -> throw IllegalArgumentException("Operation <any> is not supported for field type: ${fieldValue::class}")
            }
        } ?: false

}
