package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.Specification

fun <TEntity : Any> nonFiltered(): Specification<TEntity> {
    return Specification.TrueSpecification()
}
