package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications

sealed class EnumSpecification {
    class Eq<TEntity : Any, TValue : Enum<TValue>>(name: String, val value: TValue) : Specification.ParameterizedSpecification<TEntity>(name)
    class AnyElement<TEntity : Any, TValue : Enum<TValue>>(name: String, val value: Collection<TValue>) : Specification.ParameterizedSpecification<TEntity>(name)
    class NoneElement<TEntity : Any, TValue : Enum<TValue>>(name: String, val value: Collection<TValue>) : Specification.ParameterizedSpecification<TEntity>(name)
    class IsNull<TEntity : Any>(name: String) : Specification.ParameterizedSpecification<TEntity>(name)
}
