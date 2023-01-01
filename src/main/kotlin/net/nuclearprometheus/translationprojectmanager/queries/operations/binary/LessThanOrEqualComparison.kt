package net.nuclearprometheus.translationprojectmanager.queries.operations.binary

class LessThanOrEqualComparison<TEntity : Any>(override val field: String, override val value: String)
    : BinaryComparisonOperation<TEntity, String> {

    override fun evaluate(entity: TEntity): Boolean {
        val fieldGetter = entity::class.members.find { it.name == field }!!
        val fieldValue = fieldGetter.call(entity)

        return when (fieldValue) {
            is String -> fieldValue <= value
            is Number -> fieldValue.toDouble() <= value.toDouble()
            else -> throw IllegalArgumentException("Operation <lte> is not supported for field type: ${fieldGetter.returnType}")
        }
    }
}
