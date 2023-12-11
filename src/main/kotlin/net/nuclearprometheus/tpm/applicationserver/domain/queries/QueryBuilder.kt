package net.nuclearprometheus.tpm.applicationserver.domain.queries

import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.Order
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.dsl.OrderByBuilder
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.Specification

class QueryBuilder<TEntity : Any> {
    private var where: Specification<TEntity>? = null
    private var orderBy: List<Order<TEntity>>? = null
    private var page: Pair<Int, Int>? = null
    private var offset: Int? = null
    private var limit: Int? = null

    fun <TBuilder : SpecificationBuilder<TEntity>> where(builder: TBuilder, block: TBuilder.() -> Specification<TEntity>) {
        where = builder.block()
    }

    fun <TBuilder : OrderByBuilder<TEntity>> orderBy(builder: TBuilder, block: TBuilder.() -> List<Order<TEntity>>) {
        orderBy = builder.block()
    }

    fun page(page: Int, size: Int) {
        this.page = page to size
    }

    fun offset(offset: Int) {
        this.offset = offset
    }

    fun limit(limit: Int) {
        this.limit = limit
    }

    fun build(): Query<TEntity> {
        return Query(where, orderBy, page, offset, limit)
    }
}
