package net.nuclearprometheus.translationprojectmanager.queries.operations.unary

class EmptyComparison<TEntity : Any>(override val field: String) : UnaryComparisonOperation<TEntity> {

    override fun evaluate(entity: TEntity): Boolean {
        val fieldGetter = entity::class.members.find { it.name == field }!!

        return when (val fieldValue = fieldGetter.call(entity)) {
            is String -> fieldValue.isEmpty()
            is Collection<*> -> fieldValue.isEmpty()
            else -> throw IllegalArgumentException("Operation <contains> is not supported for field type: ${fieldGetter.returnType}")
        }
    }
}
