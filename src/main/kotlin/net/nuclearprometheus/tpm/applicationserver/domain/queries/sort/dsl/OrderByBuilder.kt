package net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.dsl

import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.Order
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.Direction

abstract class OrderByBuilder<TEntity : Any> {

    fun sort(name: String): SortBuilder<TEntity> {
        return SortBuilder(name)
    }

    class SortBuilder<TEntity : Any>(private val name: String) {

        val ascending: Order<TEntity>
            get() = Order(name, Direction.ASC)

        val descending: Order<TEntity>
            get() = Order(name, Direction.DESC)
    }

    infix fun Order<TEntity>.and(other: Order<TEntity>): List<Order<TEntity>> {
        return listOf(this, other)
    }

    infix fun List<Order<TEntity>>.and(other: Order<TEntity>): List<Order<TEntity>> {
        return this + other
    }
}
