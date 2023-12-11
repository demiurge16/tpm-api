package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications

sealed class EnumSpecification {
    class Eq<TEntity : Any, TValue : Enum<TValue>>(name: String, value: TValue) : Specification.BinarySpecification<TEntity>(name, value)
    class AnyElement<TEntity : Any, TValue : Enum<TValue>>(name: String, value: Collection<TValue>) : Specification.BinarySpecification<TEntity>(name, value)
    class NoneElement<TEntity : Any, TValue : Enum<TValue>>(name: String, value: Collection<TValue>) : Specification.BinarySpecification<TEntity>(name, value)
    class IsNull<TEntity : Any>(name: String) : Specification.UnarySpecification<TEntity>(name)
}