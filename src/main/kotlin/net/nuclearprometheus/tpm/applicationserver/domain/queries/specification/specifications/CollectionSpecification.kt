package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications

sealed class CollectionSpecification {
    class ContainsElement<TEntity : Any, TValue : Any>(name: String, val value: TValue) : Specification.ParameterizedSpecification<TEntity>(name)
    class AllElement<TEntity : Any, TValue : Any>(name: String, val value: Collection<TValue>) : Specification.ParameterizedSpecification<TEntity>(name)
    class AnyElement<TEntity : Any, TValue : Any>(name: String, val value: Collection<TValue>) : Specification.ParameterizedSpecification<TEntity>(name)
    class NoneElement<TEntity : Any, TValue : Any>(name: String, val value: Collection<TValue>) : Specification.ParameterizedSpecification<TEntity>(name)
    class IsNull<TEntity : Any>(name: String) : Specification.ParameterizedSpecification<TEntity>(name)
    class IsEmpty<TEntity : Any>(name: String) : Specification.ParameterizedSpecification<TEntity>(name)
}

