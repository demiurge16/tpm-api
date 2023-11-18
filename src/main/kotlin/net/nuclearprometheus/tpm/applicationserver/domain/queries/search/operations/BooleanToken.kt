package net.nuclearprometheus.tpm.applicationserver.domain.queries.search.operations

import net.nuclearprometheus.tpm.applicationserver.domain.queries.executors.ValueGetter
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.Operator
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.valueparsers.ValueParserFactory

class BooleanToken<TEntity : Any>(val valueGetter: ValueGetter<TEntity, Boolean?>) : Definition<TEntity>() {

    fun eq(value: Boolean): OperationNode<TEntity> = Equals(value)
    inner class Equals(val value: Boolean) : OperationNode<TEntity>() {
        override fun matches(entity: TEntity): Boolean {
            return valueGetter(entity) == value
        }
    }

    fun isNull(): OperationNode<TEntity> = IsNull()
    inner class IsNull : OperationNode<TEntity>() {
        override fun matches(entity: TEntity): Boolean {
            return valueGetter(entity) == null
        }
    }

    override fun createOperation(name: String, operator: Operator, value: String?): OperationNode<TEntity> {
        val parser = ValueParserFactory.getParser(Boolean::class)
        return when (operator) {
            Operator.EQUALS -> {
                val parsedValue = parser.parse(value) ?: throw IllegalArgumentException("Invalid value: $value")
                eq(parsedValue)
            }
            Operator.IS_NULL -> isNull()
            else -> throw IllegalArgumentException("Invalid operator: $operator")
        }
    }
}