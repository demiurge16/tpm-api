package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications

sealed class StringSpecification {
    class Eq<TEntity : Any>(name: String, val value: String) : Specification.ParameterizedSpecification<TEntity>(name)
    class Contains<TEntity : Any>(name: String, val value: String) : Specification.ParameterizedSpecification<TEntity>(name)
    class AnyElement<TEntity : Any>(name: String, val value: Collection<String>) : Specification.ParameterizedSpecification<TEntity>(name)
    class NoneElement<TEntity : Any>(name: String, val value: Collection<String>) : Specification.ParameterizedSpecification<TEntity>(name)
    class IsNull<TEntity : Any>(name: String) : Specification.ParameterizedSpecification<TEntity>(name)
    class IsEmpty<TEntity : Any>(name: String) : Specification.ParameterizedSpecification<TEntity>(name)
}
