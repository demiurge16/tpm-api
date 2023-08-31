package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.queryexecutors

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country
import net.nuclearprometheus.tpm.applicationserver.domain.queries.executors.*
import org.springframework.stereotype.Component

@Component
class CountryQueryExecutor : InMemoryQueryExecutor<Country>() {

    override val querySorters: Map<String, Comparator<Country>> = mapOf(
        "code" to Comparator { o1, o2 -> compareValues(o1.id.value, o2.id.value) },
        "name" to Comparator { o1, o2 -> o1.name.compareTo(o2.name, ignoreCase = true) }
    )

    override val queryFilters: Map<String, Map<String, FilterExecutor<Country>>> = mapOf(
        "code" to mapOf(
            "eq" to equal { it.id.value },
            "contains" to contains { it.id.value },
            "null" to isNull { it.id.value },
            "empty" to isEmpty { it.id.value }
        ),
        "name" to mapOf(
            "eq" to equal { it.name },
            "contains" to contains { it.name },
            "null" to isNull { it.name },
            "empty" to isEmpty { it.name },
        )
    )
}