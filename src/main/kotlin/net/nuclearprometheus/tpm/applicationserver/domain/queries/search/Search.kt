package net.nuclearprometheus.tpm.applicationserver.domain.queries.search

import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.operations.OperationNode

class Search<TEntity : Any>(val root: OperationNode<TEntity>) {
    fun matches(entity: TEntity): Boolean {
        return root.matches(entity)
    }
}
