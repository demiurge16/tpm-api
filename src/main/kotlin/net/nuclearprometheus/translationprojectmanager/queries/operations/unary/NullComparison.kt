package net.nuclearprometheus.translationprojectmanager.queries.operations.unary

class NullComparison<TEntity : Any>(override val field: String) : UnaryComparisonOperation<TEntity> {

    override fun evaluate(entity: TEntity): Boolean {
        val fieldGetter = entity::class.members.find { it.name == field }!!
        val fieldValue = fieldGetter.call(entity)
        return fieldValue == null
    }
}
