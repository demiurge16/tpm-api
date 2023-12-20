package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications

sealed class ComparableSpecification {
    class Eq<TEntity : Any, TValue : Comparable<TValue>>(name: String, val value: TValue) : Specification.ParameterizedSpecification<TEntity>(name)
    class Gt<TEntity : Any, TValue : Comparable<TValue>>(name: String, val value: TValue) : Specification.ParameterizedSpecification<TEntity>(name)
    class Gte<TEntity : Any, TValue : Comparable<TValue>>(name: String, val value: TValue) : Specification.ParameterizedSpecification<TEntity>(name)
    class Lt<TEntity : Any, TValue : Comparable<TValue>>(name: String, val value: TValue) : Specification.ParameterizedSpecification<TEntity>(name)
    class Lte<TEntity : Any, TValue : Comparable<TValue>>(name: String, val value: TValue) : Specification.ParameterizedSpecification<TEntity>(name)
    class AnyElement<TEntity : Any, TValue : Comparable<TValue>>(name: String, val value: Collection<TValue>) : Specification.ParameterizedSpecification<TEntity>(name)
    class NoneElement<TEntity : Any, TValue : Comparable<TValue>>(name: String, val value: Collection<TValue>) : Specification.ParameterizedSpecification<TEntity>(name)
    class IsNull<TEntity : Any>(name: String) : Specification.ParameterizedSpecification<TEntity>(name)
}
