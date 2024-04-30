package net.nuclearprometheus.tpm.applicationserver.domain.queries.sort

data class Sort<TEntity : Any>(val order: List<Order<TEntity>>)
