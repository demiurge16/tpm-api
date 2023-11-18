package net.nuclearprometheus.tpm.applicationserver.domain.queries.search.operations

class Not<TEntity : Any>(val node: OperationNode<TEntity>) : OperationNode<TEntity>() {
    override fun matches(entity: TEntity): Boolean {
        return !node.matches(entity)
    }
}