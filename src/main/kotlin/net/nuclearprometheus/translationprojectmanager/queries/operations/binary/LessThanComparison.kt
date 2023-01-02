package net.nuclearprometheus.translationprojectmanager.queries.operations.binary

class LessThanComparison<TEntity : Any>(override val field: String, override val value: String)
    : BinaryComparisonOperation<TEntity, String> {

    override fun evaluate(entity: TEntity) =
        when (val fieldValue = getFieldValue(entity)) {
            is String -> fieldValue < value
            is Number -> fieldValue.toDouble() < value.toDouble()
            else -> throw IllegalArgumentException("Operation <lt> is not supported for field type: ${fieldValue!!::class}")
        }
}
