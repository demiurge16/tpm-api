package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.Operator
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.Specification

interface SpecificationFactory<TEntity : Any> {
    fun createSpecification(field: String, operator: Operator, value: String?): Specification<TEntity>
}