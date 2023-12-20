package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.queryexecutors

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Currency
import net.nuclearprometheus.tpm.applicationserver.domain.queries.executors.*
import org.springframework.stereotype.Component

@Component
class CurrencyQueryExecutor : QueryExecutor<Currency>() {

    override val querySorters: Map<String, Comparator<Currency>> = mapOf(
        "code" to Comparator { o1, o2 -> compareValues(o1.id.value, o2.id.value) },
        "name" to Comparator { o1, o2 -> o1.name.compareTo(o2.name, ignoreCase = true) }
    )

    override val specificationExecutors: Map<String, SpecificationExecutor<Currency, *>> = mapOf(
        uniqueValue("code") { it.id.value },
        string("name") { it.name }
    )
}