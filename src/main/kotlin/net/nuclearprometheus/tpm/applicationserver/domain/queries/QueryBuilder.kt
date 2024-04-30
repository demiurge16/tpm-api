package net.nuclearprometheus.tpm.applicationserver.domain.queries

import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.Sort
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.dsl.OrderByBuilder
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.dsl.unsorted
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.nonFiltered
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.Specification

class QueryBuilder<TEntity : Any> {
    private var where: Specification<TEntity>? = null
    private var orderBy: Sort<TEntity>? = null
    private var page: Int? = null
    private var size: Int? = null

    fun where(block: () -> Specification<TEntity>) {
        where = block()
    }

    fun <TBuilder : OrderByBuilder<TEntity>> orderBy(builder: TBuilder, block: TBuilder.() -> Sort<TEntity>) {
        orderBy = builder.block()
    }

    fun page(page: Int, size: Int) {
        this.page = page
        this.size = size
    }

    fun build(): Query<TEntity> {
        return Query(where ?: nonFiltered(), orderBy ?: unsorted(), page, size)
    }
}
