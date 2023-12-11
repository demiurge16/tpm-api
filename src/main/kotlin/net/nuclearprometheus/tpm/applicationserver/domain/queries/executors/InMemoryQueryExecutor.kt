package net.nuclearprometheus.tpm.applicationserver.domain.queries.executors

import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.Operation
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.Search
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.Order
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.Direction

typealias ValueGetter<TEntity, TValue> = (TEntity) -> TValue
typealias FilterExecutor<TEntity> = (entity: TEntity, value: Any) -> Boolean

abstract class InMemoryQueryExecutor<TEntity : Any> {

    protected abstract val querySorters: Map<String, Comparator<TEntity>>
    protected abstract val queryFilters: Map<String, Map<String, FilterExecutor<TEntity>>>

    private fun sortComparator(order: List<Order>): Comparator<TEntity> {
        if (order.isEmpty()) {
            return Comparator { _, _ -> 0 }
        }

        return order.map {
            val (field, direction) = it
            val sortComparator = querySorters[field] ?: throw IllegalArgumentException("Invalid sort expression: $field")
            if (direction == Direction.DESC) {
                sortComparator.reversed()
            } else {
                sortComparator
            }
        }.reduce { acc, comparator -> acc.thenComparing(comparator) }
    }

    private fun filterPredicate(search: Search<TEntity>): (TEntity) -> Boolean {
        return { entity: TEntity ->
            val stack = mutableListOf<Boolean>()
            if (search.operationStack.isEmpty()) {
                stack.add(true)
            }

            search.operationStack.forEach { operation ->
                when (operation) {
                    is Operation.Comparison<*> -> {
                        val filter = (operation as Operation.Comparison<TEntity>).filter
                        val (field, operator, value) = filter
                        val availablePredicates = queryFilters[field]
                            ?: throw IllegalArgumentException("Invalid filter expression: $field:$operator")
                        val filterOperation = availablePredicates[operator.symbol]
                            ?: throw IllegalArgumentException("Invalid filter expression: $field:$operator")

                        stack.add(filterOperation(entity, value))
                    }
                    is Operation.And -> {
                        val right = stack.removeLast()
                        val left = stack.removeLast()
                        stack.add(left && right)
                    }
                    is Operation.Or -> {
                        val right = stack.removeLast()
                        val left = stack.removeLast()
                        stack.add(left || right)
                    }
                    is Operation.Not -> {
                        val value = stack.removeLast()
                        stack.add(!value)
                    }
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

