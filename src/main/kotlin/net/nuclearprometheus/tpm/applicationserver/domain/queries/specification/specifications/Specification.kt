package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications

sealed class Specification<TEntity : Any> {
    class AndSpecification<TEntity : Any>(val left: Specification<TEntity>, val right: Specification<TEntity>) : Specification<TEntity>()
    class OrSpecification<TEntity : Any>(val left: Specification<TEntity>, val right: Specification<TEntity>) : Specification<TEntity>()
    class NotSpecification<TEntity : Any>(val specification: Specification<TEntity>) : Specification<TEntity>()

    abstract class UnarySpecification<TEntity : Any>(val name: String) : Specification<TEntity>()
    abstract class BinarySpecification<TEntity : Any, TValue : Any>(val name: String, val value: TValue?) : Specification<TEntity>()
}

