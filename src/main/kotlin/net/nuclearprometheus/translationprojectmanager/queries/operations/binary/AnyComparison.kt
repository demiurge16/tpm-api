package net.nuclearprometheus.translationprojectmanager.queries.operations.binary

class AnyComparison<TEntity : Any>(override val field: String, override val value: List<String>)
    : BinaryComparisonOperation<TEntity, List<String>> {

    override fun evaluate(entity: TEntity) =
        when (val fieldValue = getFieldValue(entity)) {
            is String -> fieldValue in value
            is Collection<*> -> fieldValue.map { it.toString() }
                .intersect(value.map { it.removeSurrounding("\"") }.toSet())
                .isNotEmpty()
            else -> throw IllegalArgumentException("Operation <any> is not supported for field type: ${fieldValue!!::class}")
        }
}
