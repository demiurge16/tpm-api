package net.nuclearprometheus.tpm.applicationserver.domain.queries.search.operations

abstract class OperationNode<TEntity : Any> {

    abstract fun matches(entity: TEntity): Boolean
}
