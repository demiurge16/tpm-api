package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications

sealed class BooleanSpecification {
    class Eq<TEntity : Any>(name: String, value: Boolean) : Specification.BinarySpecification<TEntity, Boolean>(name, value)
    class IsNull<TEntity : Any>(name: String) : Specification.UnarySpecification<TEntity>(name)
}
