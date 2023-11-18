package net.nuclearprometheus.tpm.applicationserver.domain.queries.search.dsl

import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.Operator
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.operations.Definition
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.operations.OperationNode

class SearchSpecification<TEntity : Any>(private val definitions: Map<String, Definition<TEntity>>) {
    fun createOperation(name: String, operator: Operator, value: String?): OperationNode<TEntity> {
        return definitions[name]?.createOperation(name, operator, value) ?: throw IllegalArgumentException("Invalid field: $name")
    }
}
