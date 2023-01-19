package net.nuclearprometheus.tpm.applicationserver.queries.operations.binary

class ContainsComparison<TEntity : Any>(override val field: String, override val value: String)
    : BinaryComparisonOperation<TEntity, String> {

    override fun evaluate(entity: TEntity) =
        when (val fieldValue = getFieldValue(entity)) {
            is String -> fieldValue.contains(value)
            is Collection<*> -> fieldValue.contains(value)
            else -> throw IllegalArgumentException("Operation <contains> is not supported for field type: ${fieldValue!!::class}")
        }

}
