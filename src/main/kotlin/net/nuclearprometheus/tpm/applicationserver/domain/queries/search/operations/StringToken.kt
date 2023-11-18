package net.nuclearprometheus.tpm.applicationserver.domain.queries.search.operations

import net.nuclearprometheus.tpm.applicationserver.domain.queries.executors.ValueGetter
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.Operator
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.valueparsers.ValueParserFactory

class StringToken<TEntity : Any>(val valueGetter: ValueGetter<TEntity, String?>): Definition<TEntity>() {

    infix fun eq(value: String): OperationNode<TEntity> = Equals(value)
    inner class Equals(val value: String) : OperationNode<TEntity>() {
        override fun matches(entity: TEntity): Boolean {
            return valueGetter(entity) == value
        }
    }

    fun contains(value: String): OperationNode<TEntity> = Contains(value)
    inner class Contains(val value: String) : OperationNode<TEntity>() {
        override fun matches(entity: TEntity): Boolean {
            return valueGetter(entity)?.contains(value, ignoreCase = true) ?: false
        }
    }

    fun any(values: Collection<String>): OperationNode<TEntity> = Any(values)
    fun any(vararg values: String): OperationNode<TEntity> = any(values.toList())
    inner class Any(val values: Collection<String>) : OperationNode<TEntity>() {
        override fun matches(entity: TEntity): Boolean {
            return valueGetter(entity) in values
        }
    }

    fun none(vararg values: String): OperationNode<TEntity> = None(values.toList())
    fun none(values: Collection<String>): OperationNode<TEntity> = None(values)
    inner class None(val values: Collection<String>) : OperationNode<TEntity>() {
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

    fun isEmpty(): OperationNode<TEntity> = IsEmpty()
    inner class IsEmpty : OperationNode<TEntity>() {
        override fun matches(entity: TEntity): Boolean {
            return valueGetter(entity)?.isEmpty() ?: false
        }
    }

    override fun createOperation(name: String, operator: Operator, value: String?): OperationNode<TEntity> {
        val parser = ValueParserFactory.getParser(String::class)
        return when (operator) {
            Operator.EQUALS -> {
                val parsedValue = parser.parse(value) ?: throw IllegalArgumentException("Invalid value: $value")
                eq(parsedValue)
            }
            Operator.CONTAINS -> {
                val parsedValue = parser.parse(value) ?: throw IllegalArgumentException("Invalid value: $value")
                contains(parsedValue)
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