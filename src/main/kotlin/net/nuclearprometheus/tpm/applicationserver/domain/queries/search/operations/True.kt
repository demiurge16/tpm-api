package net.nuclearprometheus.tpm.applicationserver.domain.queries.search.operations

class True<TEntity : Any> : OperationNode<TEntity>() {
    override fun matches(entity: TEntity): Boolean {
        return true
    }
}