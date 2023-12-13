package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.requests

import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.SortParser
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.dsl.unsorted
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.SpecificationParser
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.nonFiltered

abstract class FilteredRequest<TEntity : Any>(
    val page: Int?,
    val size: Int?,
    val sort: String?,
    val search: String?
) {

    fun toQuery(specificationBuilder: SpecificationBuilder<TEntity>) = Query(
        page = page,
        size = size,
        sort = sort?.let { SortParser.parseSort(it) } ?: unsorted(),
        specification = search?.let { SpecificationParser.parseSpecification(it, specificationBuilder) } ?: nonFiltered()
    )
}
