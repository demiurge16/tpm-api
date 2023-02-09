package net.nuclearprometheus.tpm.applicationserver.queries.operations.binary

class AllComparison<TEntity : Any>(override val field: String, override val value: List<String>)
    : BinaryComparisonOperation<TEntity, List<String>> {

    override fun evaluate(entity: TEntity) =
        getFieldValue(entity)?.let { fieldValue ->
            when (fieldValue) {
                is Collection<*> -> fieldValue.map { it.toString() }
                    .containsAll(value.map { it.removeSurrounding("\"") }.toSet())

                else -> throw IllegalArgumentException("Operation <all> is not supported for field type: ${fieldValue::class}")
            }
        } ?: false


}
