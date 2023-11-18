package net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.dsl

class SortSpecificationBuilder<TEntity : Any> {
    private val sorters = mutableMapOf<String, Comparator<TEntity>>()

    fun sorter(name: String): SorterBuilder {
        return SorterBuilder(name)
    }

    inner class SorterBuilder(private val name: String) {
        infix fun using(sorter: Comparator<TEntity>) {
            sorters[name] = sorter
        }
    }

    fun build(): SortSpecification<TEntity> {
        return SortSpecification(sorters)
    }
}