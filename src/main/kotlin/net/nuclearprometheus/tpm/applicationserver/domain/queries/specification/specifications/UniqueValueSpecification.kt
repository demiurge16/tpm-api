package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications

sealed class UniqueValueSpecification {
    class Eq<TEntity : Any, TValue : Any>(name: String, value: TValue?) : Specification.BinarySpecification<TEntity, TValue>(name, value)
    class AnyElement<TEntity : Any, TValue : Any>(name: String, value: Collection<TValue?>) : Specification.BinarySpecification<TEntity, TValue>(name, value)
    class NoneElement<TEntity : Any, TValue : Any>(name: String, value: Collection<TValue?>) : Specification.BinarySpecification<TEntity, TValue>(name, value)
    class IsNull<TEntity : Any>(name: String) : Specification.UnarySpecification<TEntity>(name)
}