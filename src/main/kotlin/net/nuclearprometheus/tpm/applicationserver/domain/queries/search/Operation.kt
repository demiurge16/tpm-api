package net.nuclearprometheus.tpm.applicationserver.domain.queries.search

sealed class Operation<TEntity : Any> {

    class Not<TEntity : Any> : Operation<TEntity>() {

        override fun equals(other: Any?) = other is Not<*>
        override fun hashCode() = javaClass.hashCode()
    }

    class And<TEntity : Any> : Operation<TEntity>() {

        override fun equals(other: Any?) = other is And<*>
        override fun hashCode() = javaClass.hashCode()
    }

    class Or<TEntity : Any> : Operation<TEntity>() {

        override fun equals(other: Any?) = other is Or<*>
        override fun hashCode() = javaClass.hashCode()
    }

    abstract class Comparison<TEntity : Any>(val filter: Filter) : Operation<TEntity>() {

        override fun equals(other: Any?) = other is Comparison<*> && other.filter == filter
        override fun hashCode() = filter.hashCode()
    }

    class Equals<TEntity : Any>(field: String, value: Any)
        : Comparison<TEntity>(Filter(field, OperationType.EQUALS, value))

    class Contains<TEntity : Any>(field: String, value: Any)
        : Comparison<TEntity>(Filter(field, OperationType.CONTAINS, value))

    class GreaterThan<TEntity : Any>(field: String, value: Any)
        : Comparison<TEntity>(Filter(field, OperationType.GREATER_THAN, value))

    class GreaterThanOrEqual<TEntity : Any>(field: String, value: Any)
        : Comparison<TEntity>(Filter(field, OperationType.GREATER_THAN_OR_EQUAL, value))

    class LessThan<TEntity : Any>(field: String, value: Any)
        : Comparison<TEntity>(Filter(field, OperationType.LESS_THAN, value))

    class LessThanOrEqual<TEntity : Any>(field: String, value: Any)
        : Comparison<TEntity>(Filter(field, OperationType.LESS_THAN_OR_EQUAL, value))

    class AnyElement<TEntity : Any>(field: String, value: List<Any>)
        : Comparison<TEntity>(Filter(field, OperationType.ANY, value))

    class AllElements<TEntity : Any>(field: String, value: List<Any>)
        : Comparison<TEntity>(Filter(field, OperationType.ALL, value))

    class NoneElement<TEntity : Any>(field: String, value: List<Any>)
        : Comparison<TEntity>(Filter(field, OperationType.NONE, value))

    class IsNull<TEntity : Any>(field: String)
        : Comparison<TEntity>(Filter(field, OperationType.IS_NULL, true))

    class IsEmpty<TEntity : Any>(field: String)
        : Comparison<TEntity>(Filter(field, OperationType.IS_EMPTY, true))
}
