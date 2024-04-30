package net.nuclearprometheus.tpm.applicationserver.domain.queries.sort

import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.dsl.unsorted

object SortParser {
    fun <TEntity : Any> parseSort(sort: String): Sort<TEntity> {
        if (sort.isEmpty()) {
            return unsorted()
        }

        val expressionTokens = sort.split("&")
        val orders = expressionTokens.map { token ->
            val (name, direction) = token.split(":")
            Order<TEntity>(name, if (direction == "desc") Direction.DESC else Direction.ASC)
        }
        return Sort(orders)
    }
}