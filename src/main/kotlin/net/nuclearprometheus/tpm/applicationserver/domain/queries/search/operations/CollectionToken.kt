package net.nuclearprometheus.tpm.applicationserver.domain.queries.search.operations

import net.nuclearprometheus.tpm.applicationserver.domain.queries.executors.ValueGetter
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.Operator
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.valueparsers.ValueParserFactory
import kotlin.reflect.KClass

class CollectionToken<TEntity : Any, TValue : Any>(
    val valueGetter: ValueGetter<TEntity, Collection<TValue>?>,
    val valueClass: KClass<TValue>
): Definition<TEntity>() {
    fun all(values: Collection<TValue>): OperationNode<TEntity> = All(values)
    fun all(vararg values: TValue): OperationNode<TEntity> = All(values.toList())
    inner class All(val values: Collection<TValue>) : OperationNode<TEntity>() {
        override fun matches(entity: TEntity): Boolean {
            val collection = valueGetter(entity)
            return collection != null && collection.all { it in values }
        }
    }

    fun any(values: Collection<TValue>): OperationNode<TEntity> = Any(values)
    fun any(vararg values: TValue): OperationNode<TEntity> = Any(values.toList())
    inner class Any(val values: Collection<TValue>) : OperationNode<TEntity>() {
        override fun matches(entity: TEntity): Boolean {
            val collection = valueGetter(entity)
            return collection != null && collection.any { it in values }
        }
    }

    fun none(values: Collection<TValue>): OperationNode<TEntity> = None(values)
    fun none(vararg values: TValue): OperationNode<TEntity> = None(values.toList())
    inner class None(val values: Collection<TValue>) : OperationNode<TEntity>() {
        override fun matches(entity: TEntity): Boolean {
            val collection = valueGetter(entity)
            return collection != null && collection.none { it in values }
        }
    }

    fun isNull(): OperationNode<TEntity> = IsNull()
    inner class IsNull : OperationNode<TEntity>() {
        override fun matches(entity: TEntity): Boolean {
            return valueGetter(entity) == null
        }
    }

    fun isEmpty(): OperationNode<TEntity> = IsEmpty()
    inner class IsEmpty : OperationNode<TEntity>() {
        override fun matches(entity: TEntity): Boolean {
            val collection = valueGetter(entity)
            return collection?.isEmpty() ?: false
        }
    }

    override fun createOperation(name: String, operator: Operator, value: String?): OperationNode<TEntity> {
        val parser = ValueParserFactory.getParser(valueClass)
        return when (operator) {
            Operator.ALL -> {
                val parsedValue = parser.parseCollection(value) ?: throw IllegalArgumentException("Invalid value: $value")
                all(parsedValue)
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
            Operator.IS_EMPTY -> isEmpty()
            else -> throw IllegalArgumentException("Invalid operator: $operator")
        }
    }
}