package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications

sealed class CollectionSpecification {
    class AllElement<TEntity : Any, TValue : Any>(name: String, value: Collection<TValue>) : Specification.BinarySpecification<TEntity>(name, value)
    class AnyElement<TEntity : Any, TValue : Any>(name: String, value: Collection<TValue>) : Specification.BinarySpecification<TEntity>(name, value)
    class NoneElement<TEntity : Any, TValue : Any>(name: String, value: Collection<TValue>) : Specification.BinarySpecification<TEntity>(name, value)
    class IsNull<TEntity : Any>(name: String) : Specification.UnarySpecification<TEntity>(name)
    class IsEmpty<TEntity : Any>(name: String) : Specification.UnarySpecification<TEntity>(name)
}