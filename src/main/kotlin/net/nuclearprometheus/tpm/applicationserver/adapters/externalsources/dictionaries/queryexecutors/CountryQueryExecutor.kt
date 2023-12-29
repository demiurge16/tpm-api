package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.queryexecutors

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country
import net.nuclearprometheus.tpm.applicationserver.domain.queries.executors.*
import org.springframework.stereotype.Component

@Component
class CountryQueryExecutor : QueryExecutor<Country>() {

    override val querySorters = sortExecutors<Country> {
        sort("code", compareBy { it.id.value })
        sort("name", compareBy(String.CASE_INSENSITIVE_ORDER) { it.name.official })
    }

    override val specificationExecutors = specificationExecutors<Country> {
        uniqueValue("code") { it.id.value }
        string("name") { it.name.official }
    }
}