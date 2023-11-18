package net.nuclearprometheus.tpm.applicationserver.domain.queries.executors

import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.dsl.SearchSpecification
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.SortDirection
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.Sorter
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.dsl.SortSpecification

typealias ValueGetter<TEntity, TValue> = (TEntity) -> TValue

abstract class InMemoryQueryExecutor<TEntity : Any> {

    protected abstract val querySorters: SortSpecification<TEntity>
    protected abstract val searchSpecification: SearchSpecification<TEntity>

    private fun sortComparator(sort: List<Sorter>): Comparator<TEntity> {
        if (sort.isEmpty()) {
            return Comparator { _, _ -> 0 }
        }

        return sort.map {
            val (field, direction) = it
            val sortComparator = querySorters.getSorter(field)
            if (direction == SortDirection.DESC) {
                sortComparator.reversed()
            } else {
                sortComparator
            }
        }.reduce { acc, comparator -> acc.thenComparing(comparator) }
    }


    fun execute(query: Query<TEntity>, items: List<TEntity>): Page<TEntity> {
        return execute(query) { items }
    }

    fun execute(query: Query<TEntity>, supplier: () -> List<TEntity>): Page<TEntity> {
        val (page, size, sort, search) = query
        val filteredItems = supplier().asSequence()
            .filter { search.matches(it) }
            .sortedWith(sortComparator(sort.sorters))
            .toList()

        val totalItems = filteredItems.size
        val totalPages = totalItems / (size ?: 1)
        val currentPage = page ?: 0

        return Page(
            items = filteredItems.drop(currentPage * (size ?: 0)).take(size ?: Int.MAX_VALUE),
            currentPage = currentPage,
            totalPages = totalPages,
            totalItems = totalItems.toLong()
        )
    }
}

