package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications

sealed class ComparableSpecification {
    class Eq<TEntity : Any, TValue : Comparable<TValue>>(name: String, value: TValue) : Specification.BinarySpecification<TEntity>(name, value)
    class Gt<TEntity : Any, TValue : Comparable<TValue>>(name: String, value: TValue) : Specification.BinarySpecification<TEntity>(name, value)
    class Gte<TEntity : Any, TValue : Comparable<TValue>>(name: String, value: TValue) : Specification.BinarySpecification<TEntity>(name, value)
    class Lt<TEntity : Any, TValue : Comparable<TValue>>(name: String, value: TValue) : Specification.BinarySpecification<TEntity>(name, value)
    class Lte<TEntity : Any, TValue : Comparable<TValue>>(name: String, value: TValue) : Specification.BinarySpecification<TEntity>(name, value)
    class AnyElement<TEntity : Any, TValue : Comparable<TValue>>(name: String, value: Collection<TValue>) : Specification.BinarySpecification<TEntity>(name, value)
    class NoneElement<TEntity : Any, TValue : Comparable<TValue>>(name: String, value: Collection<TValue>) : Specification.BinarySpecification<TEntity>(name, value)
    class IsNull<TEntity : Any>(name: String) : Specification.UnarySpecification<TEntity>(name)
}