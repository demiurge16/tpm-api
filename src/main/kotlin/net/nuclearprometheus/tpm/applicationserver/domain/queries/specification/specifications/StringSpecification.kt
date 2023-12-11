package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications

sealed class StringSpecification {
    class Eq<TEntity : Any>(name: String, value: String) : Specification.BinarySpecification<TEntity>(name, value)
    class Contains<TEntity : Any>(name: String, value: String) : Specification.BinarySpecification<TEntity>(name, value)
    class AnyElement<TEntity : Any>(name: String, value: Collection<String>) : Specification.BinarySpecification<TEntity>(name, value)
    class NoneElement<TEntity : Any>(name: String, value: Collection<String>) : Specification.BinarySpecification<TEntity>(name, value)
    class IsNull<TEntity : Any>(name: String) : Specification.UnarySpecification<TEntity>(name)
    class IsEmpty<TEntity : Any>(name: String) : Specification.UnarySpecification<TEntity>(name)
}