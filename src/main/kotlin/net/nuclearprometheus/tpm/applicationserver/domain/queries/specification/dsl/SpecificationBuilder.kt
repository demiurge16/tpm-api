package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.Operator
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.*
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParserFactory
import kotlin.reflect.KClass

abstract class SpecificationBuilder<TEntity : Any> {

    protected val specificationFactories = mutableMapOf<String, SpecificationFactory<TEntity>>()

    protected fun registerSpecificationFactory(name: String, specificationBuilder: SpecificationFactory<TEntity>) {
        specificationFactories[name] = specificationBuilder
    }

    fun createSpecification(field: String, operator: Operator, value: String?): Specification<TEntity> {
        val builder = specificationFactories[field] ?: throw IllegalArgumentException("Unknown field $field")
        return builder.createSpecification(field, operator, value)
    }

    infix fun <TEntity : Any> Specification<TEntity>.and(other: Specification<TEntity>): Specification<TEntity> {
        return Specification.AndSpecification(this, other)
    }

    infix fun <TEntity : Any> Specification<TEntity>.or(other: Specification<TEntity>): Specification<TEntity> {
        return Specification.OrSpecification(this, other)
    }

    fun <TEntity : Any> not(specification: Specification<TEntity>): Specification<TEntity> {
        return Specification.NotSpecification(specification)
    }

    fun <TValue : Any> uniqueValue(name: String, valueClass: KClass<TValue>): UniqueValueSpecificationBuilder<TValue> {
        val builder = UniqueValueSpecificationBuilder(name, valueClass)
        registerSpecificationFactory(name, builder)
        return builder
    }

    inner class UniqueValueSpecificationBuilder<TValue : Any>(val name: String, val valueClass: KClass<TValue>) : SpecificationFactory<TEntity> {

        override fun createSpecification(field: String, operator: Operator, value: String?): Specification<TEntity> {
            val valueParser = ValueParserFactory.getParser(valueClass)
            return when (operator) {
                Operator.EQUALS -> {
                    val parsedValue = requireNotNull(valueParser.parse(value)) { "Value must not be null for specification $field:$operator. For null values use IS_NULL operator" }
                    eq(parsedValue)
                }
                Operator.ANY -> any(valueParser.parseCollection(value))
                Operator.NONE -> none(valueParser.parseCollection(value))
                Operator.IS_NULL -> isNull()
                else -> throw IllegalArgumentException("Unknown operator $operator")
            }
        }

        infix fun eq(value: TValue): Specification<TEntity> {
            return UniqueValueSpecification.Eq(name, value)
        }

        infix fun any(value: Collection<TValue>): Specification<TEntity> {
            return UniqueValueSpecification.AnyElement(name, value)
        }

        fun any(vararg value: TValue): Specification<TEntity> {
            return UniqueValueSpecification.AnyElement(name, value.toList())
        }

        infix fun none(value: Collection<TValue>): Specification<TEntity> {
            return UniqueValueSpecification.NoneElement(name, value)
        }

        fun none(vararg value: TValue): Specification<TEntity> {
            return UniqueValueSpecification.NoneElement(name, value.toList())
        }

        fun isNull(): Specification<TEntity> {
            return UniqueValueSpecification.IsNull(name)
        }
    }

    fun string(name: String): StringSpecificationBuilder {
        val builder = StringSpecificationBuilder(name)
        registerSpecificationFactory(name, builder)
        return builder
    }

    inner class StringSpecificationBuilder(val name: String) : SpecificationFactory<TEntity> {

        override fun createSpecification(field: String, operator: Operator, value: String?): Specification<TEntity> {
            val valueParser = ValueParserFactory.getParser(String::class)
            return when (operator) {
                Operator.EQUALS -> {
                    val parsedValue = requireNotNull(valueParser.parse(value)) { "Value must not be null for specification $field:$operator. For null values use IS_NULL operator" }
                    eq(parsedValue)
                }
                Operator.CONTAINS -> {
                    val parsedValue = requireNotNull(valueParser.parse(value)) { "Value must not be null for specification $field:$operator. For null values use IS_NULL operator" }
                    contains(parsedValue)
                }
                Operator.ANY -> any(valueParser.parseCollection(value))
                Operator.NONE -> none(valueParser.parseCollection(value))
                Operator.IS_NULL -> isNull()
                Operator.IS_EMPTY -> isEmpty()
                else -> throw IllegalArgumentException("Unknown operator $operator")
            }
        }

        infix fun eq(value: String): Specification<TEntity> {
            return StringSpecification.Eq(name, value)
        }

        infix fun contains(value: String): Specification<TEntity> {
            return StringSpecification.Contains(name, value)
        }

        infix fun any(value: Collection<String>): Specification<TEntity> {
            return StringSpecification.AnyElement(name, value)
        }

        fun any(vararg value: String): Specification<TEntity> {
            return StringSpecification.AnyElement(name, value.toList())
        }

        infix fun none(value: Collection<String>): Specification<TEntity> {
            return StringSpecification.NoneElement(name, value)
        }

        fun none(vararg value: String): Specification<TEntity> {
            return StringSpecification.NoneElement(name, value.toList())
        }

        fun isNull(): Specification<TEntity> {
            return StringSpecification.IsNull(name)
        }

        fun isEmpty(): Specification<TEntity> {
            return StringSpecification.IsEmpty(name)
        }
    }

    fun <TValue : Comparable<TValue>> comparable(name: String, valueClass: KClass<TValue>): ComparableSpecificationBuilder<TValue> {
        val builder = ComparableSpecificationBuilder(name, valueClass)
        registerSpecificationFactory(name, builder)
        return builder
    }

    inner class ComparableSpecificationBuilder<TValue : Comparable<TValue>>(val name: String, val valueClass: KClass<TValue>): SpecificationFactory<TEntity> {

        override fun createSpecification(field: String, operator: Operator, value: String?): Specification<TEntity> {
            val valueParser = ValueParserFactory.getParser(valueClass)
            return when (operator) {
                Operator.EQUALS -> {
                    val parsedValue = requireNotNull(valueParser.parse(value)) { "Value must not be null for specification $field:$operator. For null values use IS_NULL operator" }
                    eq(parsedValue)
                }
                Operator.GREATER_THAN -> {
                    val parsedValue = requireNotNull(valueParser.parse(value)) { "Value must not be null for specification $field:$operator. For null values use IS_NULL operator" }
                    gt(parsedValue)
                }
                Operator.GREATER_THAN_OR_EQUAL -> {
                    val parsedValue = requireNotNull(valueParser.parse(value)) { "Value must not be null for specification $field:$operator. For null values use IS_NULL operator" }
                    gte(parsedValue)
                }
                Operator.LESS_THAN -> {
                    val parsedValue = requireNotNull(valueParser.parse(value)) { "Value must not be null for specification $field:$operator. For null values use IS_NULL operator" }
                    lt(parsedValue)
                }
                Operator.LESS_THAN_OR_EQUAL -> {
                    val parsedValue = requireNotNull(valueParser.parse(value)) { "Value must not be null for specification $field:$operator. For null values use IS_NULL operator" }
                    lte(parsedValue)
                }
                Operator.ANY -> any(valueParser.parseCollection(value))
                Operator.NONE -> none(valueParser.parseCollection(value))
                Operator.IS_NULL -> isNull()
                else -> throw IllegalArgumentException("Unknown operator $operator")
            }
        }

        infix fun eq(value: TValue): Specification<TEntity> {
            return ComparableSpecification.Eq(name, value)
        }

        infix fun gt(value: TValue): Specification<TEntity> {
            return ComparableSpecification.Gt(name, value)
        }

        infix fun gte(value: TValue): Specification<TEntity> {
            return ComparableSpecification.Gte(name, value)
        }

        infix fun lt(value: TValue): Specification<TEntity> {
            return ComparableSpecification.Lt(name, value)
        }

        infix fun lte(value: TValue): Specification<TEntity> {
            return ComparableSpecification.Lte(name, value)
        }

        infix fun any(value: Collection<TValue>): Specification<TEntity> {
            return ComparableSpecification.AnyElement(name, value)
        }

        fun any(vararg value: TValue): Specification<TEntity> {
            return ComparableSpecification.AnyElement(name, value.toList())
        }

        infix fun none(value: Collection<TValue>): Specification<TEntity> {
            return ComparableSpecification.NoneElement(name, value)
        }

        fun none(vararg value: TValue): Specification<TEntity> {
            return ComparableSpecification.NoneElement(name, value.toList())
        }

        fun isNull(): Specification<TEntity> {
            return ComparableSpecification.IsNull(name)
        }
    }

    fun <TValue : Any> collection(name: String, valueClass: KClass<TValue>): CollectionSpecificationBuilder<TValue> {
        val builder = CollectionSpecificationBuilder(name, valueClass)
        registerSpecificationFactory(name, builder)
        return builder
    }

    inner class CollectionSpecificationBuilder<TValue : Any>(val name: String, val valueClass: KClass<TValue>) : SpecificationFactory<TEntity> {

        override fun createSpecification(field: String, operator: Operator, value: String?): Specification<TEntity> {
            val valueParser = ValueParserFactory.getParser(valueClass)
            return when (operator) {
                Operator.CONTAINS -> {
                    val parsedValue = requireNotNull(valueParser.parse(value)) { "Value must not be null for specification $field:$operator. For null values use IS_NULL operator" }
                    contains(parsedValue)
                }
                Operator.ALL -> all(valueParser.parseCollection(value))
                Operator.ANY -> any(valueParser.parseCollection(value))
                Operator.NONE -> none(valueParser.parseCollection(value))
                Operator.IS_NULL -> isNull()
                Operator.IS_EMPTY -> isEmpty()
                else -> throw IllegalArgumentException("Unknown operator $operator")
            }
        }

        infix fun contains(value: TValue): Specification<TEntity> {
            return CollectionSpecification.ContainsElement(name, value)
        }

        infix fun all(value: Collection<TValue>): Specification<TEntity> {
            return CollectionSpecification.AllElement(name, value)
        }

        fun all(vararg value: TValue): Specification<TEntity> {
            return CollectionSpecification.AllElement(name, value.toList())
        }

        infix fun any(value: Collection<TValue>): Specification<TEntity> {
            return CollectionSpecification.AnyElement(name, value)
        }

        fun any(vararg value: TValue): Specification<TEntity> {
            return CollectionSpecification.AnyElement(name, value.toList())
        }

        infix fun none(value: Collection<TValue>): Specification<TEntity> {
            return CollectionSpecification.NoneElement(name, value)
        }

        fun none(vararg value: TValue): Specification<TEntity> {
            return CollectionSpecification.NoneElement(name, value.toList())
        }

        fun isNull(): Specification<TEntity> {
            return CollectionSpecification.IsNull(name)
        }

        fun isEmpty(): Specification<TEntity> {
            return CollectionSpecification.IsEmpty(name)
        }
    }

    fun <TValue : Enum<TValue>> enum(name: String, valueClass: KClass<TValue>): EnumSpecificationBuilder<TValue> {
        val builder = EnumSpecificationBuilder(name, valueClass)
        registerSpecificationFactory(name, builder)
        return builder
    }

    inner class EnumSpecificationBuilder<TValue : Enum<TValue>>(val name: String, val valueClass: KClass<TValue>) : SpecificationFactory<TEntity> {

        override fun createSpecification(field: String, operator: Operator, value: String?): Specification<TEntity> {
            val valueParser = ValueParserFactory.getParser(valueClass)
            return when (operator) {
                Operator.EQUALS -> {
                    val parsedValue = requireNotNull(valueParser.parse(value)) { "Value must not be null for specification $field:$operator. For null values use IS_NULL operator" }
                    eq(parsedValue)
                }
                Operator.ANY -> any(valueParser.parseCollection(value))
                Operator.NONE -> none(valueParser.parseCollection(value))
                Operator.IS_NULL -> isNull()
                else -> throw IllegalArgumentException("Unknown operator $operator")
            }
        }

        infix fun eq(value: TValue): Specification<TEntity> {
            return EnumSpecification.Eq(name, value)
        }

        infix fun any(value: Collection<TValue>): Specification<TEntity> {
            return EnumSpecification.AnyElement(name, value)
        }

        fun any(vararg value: TValue): Specification<TEntity> {
            return EnumSpecification.AnyElement(name, value.toList())
        }

        infix fun none(value: Collection<TValue>): Specification<TEntity> {
            return EnumSpecification.NoneElement(name, value)
        }

        fun none(vararg value: TValue): Specification<TEntity> {
            return EnumSpecification.NoneElement(name, value.toList())
        }

        fun isNull(): Specification<TEntity> {
            return EnumSpecification.IsNull(name)
        }
    }

    fun boolean(name: String): BooleanSpecificationBuilder {
        val builder = BooleanSpecificationBuilder(name)
        registerSpecificationFactory(name, builder)
        return builder
    }

    inner class BooleanSpecificationBuilder(val name: String) : SpecificationFactory<TEntity> {

        override fun createSpecification(field: String, operator: Operator, value: String?): Specification<TEntity> {
            val valueParser = ValueParserFactory.getParser(Boolean::class)
            return when (operator) {
                Operator.EQUALS -> {
                    val parsedValue = requireNotNull(valueParser.parse(value)) { "Value must not be null for specification $field:$operator. For null values use IS_NULL operator" }
                    eq(parsedValue)
                }
                Operator.IS_NULL -> isNull()
                else -> throw IllegalArgumentException("Unknown operator $operator")
            }
        }

        infix fun eq(value: Boolean): Specification<TEntity> {
            return BooleanSpecification.Eq(name, value)
        }

        fun isNull(): Specification<TEntity> {
            return BooleanSpecification.IsNull(name)
        }
    }

    // TODO: Allow registering custom specifications
    // TODO: Add reference specification - for example for filtering by id of referenced entity
}
