package net.nuclearprometheus.tpm.applicationserver.domain.queries.sort

data class Sorter<TEntity : Any>(val field: String, val direction: SortDirection, val sorter: Comparator<TEntity>)
