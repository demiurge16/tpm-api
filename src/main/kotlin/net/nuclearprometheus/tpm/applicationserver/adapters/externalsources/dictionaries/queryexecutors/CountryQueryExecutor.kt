package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.queryexecutors

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country
import net.nuclearprometheus.tpm.applicationserver.domain.queries.executors.*
import org.springframework.stereotype.Component

@Component
class CountryQueryExecutor : QueryExecutor<Country>() {

    override val querySorters: Map<String, Comparator<Country>> = mapOf(
        "code" to Comparator { o1, o2 -> compareValues(o1.id.value, o2.id.value) },
        "name" to Comparator { o1, o2 -> o1.name.official.compareTo(o2.name.official, ignoreCase = true) }
    )

    override val specificationExecutors: Map<String, SpecificationExecutor<Country, *>> = mapOf(
        uniqueValue("code") { it.id.value },
        string("name") { it.name.official }
    )
}