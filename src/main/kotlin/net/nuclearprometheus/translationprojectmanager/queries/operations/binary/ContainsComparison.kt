package net.nuclearprometheus.translationprojectmanager.queries.operations.binary

class ContainsComparison<TEntity : Any>(override val field: String, override val value: String)
    : BinaryComparisonOperation<TEntity, String> {

    override fun evaluate(entity: TEntity): Boolean {
        val fieldGetter = entity::class.members.find { it.name == field }!!

        return when (val fieldValue = fieldGetter.call(entity)) {
            is String -> fieldValue.contains(value)
            is Collection<*> -> fieldValue.contains(value)
            else -> throw IllegalArgumentException("Operation <contains> is not supported for field type: ${fieldGetter.returnType}")
        }
    }
}
