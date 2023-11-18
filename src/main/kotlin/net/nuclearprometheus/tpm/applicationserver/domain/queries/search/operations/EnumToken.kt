package net.nuclearprometheus.tpm.applicationserver.domain.queries.search.operations

import net.nuclearprometheus.tpm.applicationserver.domain.queries.executors.ValueGetter
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.Operator
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.valueparsers.ValueParserFactory
import kotlin.reflect.KClass

class EnumToken<TEntity : Any, TValue : Enum<TValue>>(
    val valueGetter: ValueGetter<TEntity, TValue?>,
    val valueClass: KClass<TValue>
): Definition<TEntity>() {

    fun eq(value: TValue): OperationNode<TEntity> = Equals(value)
    inner class Equals(val value: TValue) : OperationNode<TEntity>() {
        override fun matches(entity: TEntity): Boolean {
            return valueGetter(entity) == value
        }
    }

    fun any(values: Collection<TValue>): OperationNode<TEntity> = Any(values)
    fun any(vararg values: TValue): OperationNode<TEntity> = any(values.toList())
    inner class Any(val values: Collection<TValue>) : OperationNode<TEntity>() {
        override fun matches(entity: TEntity): Boolean {
            return valueGetter(entity) in values
        }
    }

    fun none(values: Collection<TValue>): OperationNode<TEntity> = None(values)
    fun none(vararg values: TValue): OperationNode<TEntity> = none(values.toList())
    inner class None(val values: Collection<TValue>) : OperationNode<TEntity>() {
        override fun matches(entity: TEntity): Boolean {
            return valueGetter(entity) !in values
        }
    }

    fun isNull(): OperationNode<TEntity> = IsNull()
    inner class IsNull : OperationNode<TEntity>() {
        override fun matches(entity: TEntity): Boolean {
            return valueGetter(entity) == null
        }
    }

    override fun createOperation(name: String, operator: Operator, value: String?): OperationNode<TEntity> {
        val parser = ValueParserFactory.getParser(valueClass)
        return when (operator) {
            Operator.EQUALS -> {
                val parsedValue = parser.parse(value) ?: throw IllegalArgumentException("Invalid value: $value")
                eq(parsedValue)
            }
            Operator.ANY -> {
                val parsedValue = parser.parseCollection(value) ?: throw IllegalArgumentException("Invalid value: $value")
                any(parsedValue)
            }
            Operator.NONE -> {
                val parsedValue = parser.parseCollection(value) ?: throw IllegalArgumentException("Invalid value: $value")
                none(parsedValue)
            }
            Operator.IS_NULL -> isNull()
            else -> throw IllegalArgumentException("Invalid operator: $operator")
        }
    }
}