package net.nuclearprometheus.translationprojectmanager.queries.operations.binary

class EqualsComparison<TEntity : Any>(override val field: String, override val value: String)
    : BinaryComparisonOperation<TEntity, String> {

    override fun evaluate(entity: TEntity) = getFieldValue(entity) == value
}
