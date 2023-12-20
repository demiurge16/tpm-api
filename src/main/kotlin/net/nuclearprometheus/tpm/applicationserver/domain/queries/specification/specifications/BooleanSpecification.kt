package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications

sealed class BooleanSpecification {
    class Eq<TEntity : Any>(name: String, val value: Boolean) : Specification.ParameterizedSpecification<TEntity>(name)
    class IsNull<TEntity : Any>(name: String) : Specification.ParameterizedSpecification<TEntity>(name)
}
