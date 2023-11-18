package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.queryexecutors

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country
import net.nuclearprometheus.tpm.applicationserver.domain.queries.executors.*
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.dsl.searchSpecification
import org.springframework.stereotype.Component

@Component
class CountryQueryExecutor : InMemoryQueryExecutor<Country>() {

    override val querySorters: Map<String, Comparator<Country>> = mapOf(
        "code" to Comparator { o1, o2 -> compareValues(o1.id.value, o2.id.value) },
        "name" to Comparator { o1, o2 -> o1.name.official.compareTo(o2.name.official, ignoreCase = true) }
    )

    override val searchSpecification = searchSpecification<Country> {
        uniqueToken("code", String::class) using { it.id.value }
        string("name") using { it.name.official }
    }
}