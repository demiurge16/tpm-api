package net.nuclearprometheus.tpm.applicationserver.domain.queries.executors

import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.Direction
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.Sort
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.Specification

typealias ValueGetter<TEntity, TValue> = (TEntity) -> TValue
typealias FilterExecutor<TEntity> = (entity: TEntity, value: Any) -> Boolean

abstract class InMemoryQueryExecutor<TEntity : Any> {

    protected abstract val querySorters: Map<String, Comparator<TEntity>>
    protected abstract val queryFilters: Map<String, Map<String, FilterExecutor<TEntity>>>

    private fun sortComparator(sort: Sort<TEntity>): Comparator<TEntity> {
        if (sort.order.isEmpty()) {
            return Comparator { _, _ -> 0 }
        }

        return sort.order.map {
            val (field, direction) = it
            val sortComparator = querySorters[field] ?: throw IllegalArgumentException("Invalid sort expression: $field")
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
                is Specification.UnarySpecification -> {
                    TODO("Implement unary specification")
                }
                is Specification.BinarySpecification<TEntity, *> -> {
                    TODO("Implement binary specification")
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

