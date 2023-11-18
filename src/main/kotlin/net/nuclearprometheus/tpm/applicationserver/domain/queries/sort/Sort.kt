package net.nuclearprometheus.tpm.applicationserver.domain.queries.sort

data class Sort<TEntity : Any>(val sorters: List<Sorter<TEntity>>)
