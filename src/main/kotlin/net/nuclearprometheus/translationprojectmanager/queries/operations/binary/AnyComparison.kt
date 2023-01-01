package net.nuclearprometheus.translationprojectmanager.queries.operations.binary

class AnyComparison<TEntity : Any>(override val field: String, override val value: List<String>)
    : BinaryComparisonOperation<TEntity, List<String>> {

    override fun evaluate(entity: TEntity): Boolean {
        val fieldGetter = entity::class.members.find { it.name == field }!!
        val fieldValue = fieldGetter.call(entity)

        return when (fieldValue) {
            is Collection<*> -> fieldValue.map { it.toString() }
                .intersect(value.map { it.removeSurrounding("\"") }.toSet())
                .isNotEmpty()
            else -> throw IllegalArgumentException("Operation <any> is not supported for field type: ${fieldGetter.returnType}")
        }
    }
}

class AllComparison<TEntity : Any>(override val field: String, override val value: List<String>)
    : BinaryComparisonOperation<TEntity, List<String>> {

    override fun evaluate(entity: TEntity): Boolean {
        val fieldGetter = entity::class.members.find { it.name == field }!!
        val fieldValue = fieldGetter.call(entity)

        return when (fieldValue) {
            is Collection<*> -> fieldValue.map { it.toString() }
                .containsAll(value.map { it.removeSurrounding("\"") }.toSet())
            else -> throw IllegalArgumentException("Operation <all> is not supported for field type: ${fieldGetter.returnType}")
        }
    }
}
