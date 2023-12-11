package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.Operator
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.*
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParserFactory
import kotlin.reflect.KClass

abstract class SpecificationBuilder<TEntity : Any> {

    abstract fun createSpecification(field: String, operator: Operator, value: String?): Specification<TEntity>

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
        return UniqueValueSpecificationBuilder(name, valueClass)
    }

    inner class UniqueValueSpecificationBuilder<TValue : Any>(val name: String, val valueClass: KClass<TValue>) : SpecificationBuilder<TEntity>() {

        override fun createSpecification(field: String, operator: Operator, value: String?): Specification<TEntity> {
            val valueParser = ValueParserFactory.getParser(valueClass)
            return when (operator) {
                Operator.EQUALS -> eq(valueParser.parse(value))
                Operator.ANY -> any(valueParser.parseCollection(value))
                Operator.NONE -> none(valueParser.parseCollection(value))
                Operator.IS_NULL -> isNull()
                else -> throw IllegalArgumentException("Unknown operator $operator")
            }
        }

        infix fun eq(value: TValue?): Specification<TEntity> {
            return UniqueValueSpecification.Eq(name, value)
        }

        infix fun any(value: Collection<TValue?>): Specification<TEntity> {
            return UniqueValueSpecification.AnyElement(name, value)
        }

        fun any(vararg value: TValue?): Specification<TEntity> {
            return UniqueValueSpecification.AnyElement(name, value.toList())
        }

        infix fun none(value: Collection<TValue?>): Specification<TEntity> {
            return UniqueValueSpecification.NoneElement(name, value)
        }

        fun none(vararg value: TValue?): Specification<TEntity> {
            return UniqueValueSpecification.NoneElement(name, value.toList())
        }

        fun isNull(): Specification<TEntity> {
            return UniqueValueSpecification.IsNull(name)
        }
    }

    fun string(name: String): StringSpecificationBuilder<TEntity> {
        return StringSpecificationBuilder(name)
    }

    inner class StringSpecificationBuilder<TEntity : Any>(val name: String): SpecificationBuilder<TEntity>() {

        override fun createSpecification(field: String, operator: Operator, value: String?): Specification<TEntity> {
            val valueParser = ValueParserFactory.getParser(String::class)
            return when (operator) {
                Operator.EQUALS -> eq(valueParser.parse(value))
                Operator.CONTAINS -> contains(valueParser.parse(value))
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

    fun <TValue : Comparable<TValue>> comparable(name: String, valueClass: KClass<TValue>): ComparableSpecificationBuilder<TEntity, TValue> {
        return ComparableSpecificationBuilder(name, valueClass)
    }

    inner class ComparableSpecificationBuilder<TEntity : Any, TValue : Comparable<TValue>>(val name: String, val valueClass: KClass<TValue>) {
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

    fun <TValue : Any> collection(name: String, valueClass: KClass<TValue>): CollectionSpecificationBuilder<TEntity, TValue> {
        return CollectionSpecificationBuilder(name, valueClass)
    }

    inner class CollectionSpecificationBuilder<TEntity : Any, TValue : Any>(val name: String, val valueClass: KClass<TValue>) {
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

    fun <TValue : Enum<TValue>> enum(name: String, valueClass: KClass<TValue>): EnumSpecificationBuilder<TEntity, TValue> {
        return EnumSpecificationBuilder(name, valueClass)
    }

    inner class EnumSpecificationBuilder<TEntity : Any, TValue : Enum<TValue>>(val name: String, val valueClass: KClass<TValue>) {
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

    fun boolean(name: String): BooleanSpecificationBuilder<TEntity> {
        return BooleanSpecificationBuilder(name)
    }

    inner class BooleanSpecificationBuilder<TEntity : Any>(val name: String) {
        infix fun eq(value: Boolean): Specification<TEntity> {
            return BooleanSpecification.Eq(name, value)
        }

        fun isNull(): Specification<TEntity> {
            return BooleanSpecification.IsNull(name)
        }
    }
}
