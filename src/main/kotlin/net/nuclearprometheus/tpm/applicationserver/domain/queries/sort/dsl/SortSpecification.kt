package net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.dsl

class SortSpecification<TEntity : Any>(private val sorters: Map<String, Comparator<TEntity>>) {

    fun getSorter(sorterName: String): Comparator<TEntity> {
        return sorters[sorterName] ?: throw IllegalArgumentException("Invalid sorter: $sorterName")
    }
}