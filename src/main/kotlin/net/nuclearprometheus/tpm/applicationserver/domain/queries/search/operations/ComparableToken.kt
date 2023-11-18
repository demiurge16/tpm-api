package net.nuclearprometheus.tpm.applicationserver.domain.queries.search.operations

import net.nuclearprometheus.tpm.applicationserver.domain.queries.executors.ValueGetter
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.Operator
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.valueparsers.ValueParserFactory
import kotlin.reflect.KClass

class ComparableToken<TEntity : Any, TValue : Comparable<TValue>>(
    val valueGetter: ValueGetter<TEntity, TValue?>,
    val valueClass: KClass<TValue>
) : Definition<TEntity>() {

    fun eq(value: TValue): OperationNode<TEntity> = Equals(value)
    inner class Equals(val value: TValue) : OperationNode<TEntity>() {
        override fun matches(entity: TEntity): Boolean {
            return valueGetter(entity) == value
        }
    }

    fun gt(value: TValue): OperationNode<TEntity> = GreaterThan(value)
    inner class GreaterThan(val value: TValue) : OperationNode<TEntity>() {
        override fun matches(entity: TEntity): Boolean {
            val entityValue = valueGetter(entity)
            return entityValue != null && entityValue > value
        }
    }

    fun gte(value: TValue): OperationNode<TEntity> = GreaterThanOrEquals(value)
    inner class GreaterThanOrEquals(val value: TValue) : OperationNode<TEntity>() {
        override fun matches(entity: TEntity): Boolean {
            val entityValue = valueGetter(entity)
            return entityValue != null && entityValue >= value
        }
    }

    fun lt(value: TValue): OperationNode<TEntity> = LessThan(value)
    inner class LessThan(val value: TValue) : OperationNode<TEntity>() {
        override fun matches(entity: TEntity): Boolean {
            val entityValue = valueGetter(entity)
            return entityValue != null && entityValue < value
        }
    }

    fun lte(value: TValue): OperationNode<TEntity> = LessThanOrEquals(value)
    inner class LessThanOrEquals(val value: TValue) : OperationNode<TEntity>() {
        override fun matches(entity: TEntity): Boolean {
            val entityValue = valueGetter(entity)
            return entityValue != null && entityValue <= value
        }
    }

    fun between(from: TValue, to: TValue): OperationNode<TEntity> = Between(from, to)
    inner class Between(val from: TValue, val to: TValue) : OperationNode<TEntity>() {
        override fun matches(entity: TEntity): Boolean {
            val entityValue = valueGetter(entity)
            return entityValue != null && entityValue in from..to
        }
    }

    fun any(values: Collection<TValue>): OperationNode<TEntity> = Any(values)
    fun any(vararg values: TValue): OperationNode<TEntity> = any(values.toList())
    inner class Any(val values: Collection<TValue>) : OperationNode<TEntity>() {
        override fun matches(entity: TEntity): Boolean {
            val entityValue = valueGetter(entity)
            return entityValue in values
        }
    }

    fun none(values: Collection<TValue>): OperationNode<TEntity> = None(values)
    fun none(vararg values: TValue): OperationNode<TEntity> = None(values.toList())
    inner class None(val values: Collection<TValue>) : OperationNode<TEntity>() {
        override fun matches(entity: TEntity): Boolean {
            val entityValue = valueGetter(entity)
            return entityValue !in values
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
            Operator.GREATER_THAN -> {
                val parsedValue = parser.parse(value) ?: throw IllegalArgumentException("Invalid value: $value")
                gt(parsedValue)
            }
            Operator.GREATER_THAN_OR_EQUAL -> {
                val parsedValue = parser.parse(value) ?: throw IllegalArgumentException("Invalid value: $value")
                gte(parsedValue)
            }
            Operator.LESS_THAN -> {
                val parsedValue = parser.parse(value) ?: throw IllegalArgumentException("Invalid value: $value")
                lt(parsedValue)
            }
            Operator.LESS_THAN_OR_EQUAL -> {
                val parsedValue = parser.parse(value) ?: throw IllegalArgumentException("Invalid value: $value")
                lte(parsedValue)
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