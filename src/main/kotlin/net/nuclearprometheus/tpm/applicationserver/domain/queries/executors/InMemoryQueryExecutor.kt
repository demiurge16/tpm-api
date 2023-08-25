package net.nuclearprometheus.tpm.applicationserver.domain.queries.executors

import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.Operation
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.Search
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.Sort
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.SortDirection

typealias ValueGetter<TEntity, TValue> = (TEntity) -> TValue
typealias FilterExecutor<TEntity> = (entity: TEntity, value: Any) -> Boolean

abstract class InMemoryQueryExecutor<TEntity : Any> {

    protected abstract val querySorters: Map<String, Comparator<TEntity>>
    protected abstract val queryFilters: Map<String, Map<String, FilterExecutor<TEntity>>>

    private fun sortComparator(sort: List<Sort>): Comparator<TEntity> {
        if (sort.isEmpty()) {
            return Comparator { _, _ -> 0 }
        }

        return sort.map {
            val (field, direction) = it
            val sortComparator = querySorters[field] ?: throw IllegalArgumentException("Invalid sort expression: $field")
            if (direction == SortDirection.DESC) {
                sortComparator.reversed()
            } else {
                sortComparator
            }
        }.reduce { acc, comparator -> acc.thenComparing(comparator) }
    }

    private fun filterPredicate(search: Search<TEntity>): (TEntity) -> Boolean {
        return { person: TEntity ->
            val stack = mutableListOf<Boolean>()
            if (search.operationStack.isEmpty()) {
                stack.add(true)
            }

            search.operationStack.forEach { operation ->
                when (operation) {
                    is Operation.Comparison<*> -> {
                        val filter = (operation as Operation.Comparison<TEntity>).filter
                        val (field, operation, value) = filter
                        val availablePredicates = queryFilters[field]
                            ?: throw IllegalArgumentException("Invalid filter expression: $field:$operation")
                        val filterOperation = availablePredicates[operation.symbol]
                            ?: throw IllegalArgumentException("Invalid filter expression: $field:$operation")

                        stack.add(filterOperation(person, value))
                    }
                    is Operation.And -> stack.add(stack.removeLast() && stack.removeLast())
                    is Operation.Or -> stack.add(stack.removeLast() || stack.removeLast())
                    is Operation.Not -> stack.add(!stack.removeLast())
                }
            }

            stack.removeLast()
        }
    }

    fun execute(query: Query<TEntity>, items: List<TEntity>): Page<TEntity> {
        return execute(query) { items }
    }

    fun execute(query: Query<TEntity>, supplier: () -> List<TEntity>): Page<TEntity> {
        val filteredItems = supplier().asSequence()
            .filter(filterPredicate(query.search))
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

