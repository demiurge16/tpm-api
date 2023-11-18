package net.nuclearprometheus.tpm.applicationserver.domain.queries.search.operations

import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.Operator

abstract class Definition<TEntity : Any> {
    abstract fun createOperation(name: String, operator: Operator, value: String?): OperationNode<TEntity>
}
