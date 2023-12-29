package net.nuclearprometheus.tpm.applicationserver.domain.queries.executors

import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.Direction
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.Sort
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.Specification

typealias ValueGetter<TEntity, TValue> = (TEntity) -> TValue?

abstract class QueryExecutor<TEntity : Any> {

    protected abstract val querySorters: SortExecutors<TEntity>
    protected abstract val specificationExecutors: SpecificationExecutors<TEntity>

    private fun sortComparator(sort: Sort<TEntity>): Comparator<TEntity> {
        if (sort.order.isEmpty()) {
            return Comparator { _, _ -> 0 }
        }

        return sort.order.map {
            val (field, direction) = it
            val sortComparator = querySorters[field]
            if (direction == Direction.DESC) {
                sortComparator.reversed()
            } else {
                sortComparator
            }
        }.reduce { acc, comparator -> acc.thenComparing(comparator) }
    }

    private fun filterPredicate(search: Specification<TEntity>): (TEntity) -> Boolean {
        return { entity: TEntity ->
            when (search) {
                is Specification.AndSpecification -> {
                    val left = filterPredicate(search.left)
                    val right = filterPredicate(search.right)
                    left(entity) && right(entity)
                }
                is Specification.OrSpecification -> {
                    val left = filterPredicate(search.left)
                    val right = filterPredicate(search.right)
                    left(entity) || right(entity)
                }
                is Specification.NotSpecification -> {
                    val spec = filterPredicate(search.specification)
                    !spec(entity)
                }
                is Specification.TrueSpecification -> {
                    true
                }
                is Specification.FalseSpecification -> {
                    false
                }
                is Specification.ParameterizedSpecification -> {
                    specificationExecutors[search.name].execute(entity, search)
                }
            }
        }
    }

    fun execute(query: Query<TEntity>, items: List<TEntity>): Page<TEntity> {
        return execute(query) { items }
    }

    fun execute(query: Query<TEntity>, supplier: () -> List<TEntity>): Page<TEntity> {
        val filteredItems = supplier().asSequence()
            .filter(filterPredicate(query.specification))
            .sortedWith(sortComparator(query.sort))
            .toList()

        val totalItems = filteredItems.size
        val totalPages = totalItems / (query.size ?: 1)
        val currentPage = query.page ?: 0

        return Page(
            items = filteredItems.drop(currentPage * (query.size ?: 0)).take(query.size ?: Int.MAX_VALUE),
            currentPage = currentPage,
            totalPages = totalPages,
            totalItems = totalItems.toLong()
        )
    }
}

