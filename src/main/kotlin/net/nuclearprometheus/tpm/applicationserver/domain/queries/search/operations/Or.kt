package net.nuclearprometheus.tpm.applicationserver.domain.queries.search.operations

class Or<TEntity : Any>(val left: OperationNode<TEntity>, val right: OperationNode<TEntity>) : OperationNode<TEntity>() {
    override fun matches(entity: TEntity): Boolean {
        return left.matches(entity) || right.matches(entity)
    }
}