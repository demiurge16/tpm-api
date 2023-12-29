package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.queryexecutors

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Currency
import net.nuclearprometheus.tpm.applicationserver.domain.queries.executors.*
import org.springframework.stereotype.Component

@Component
class CurrencyQueryExecutor : QueryExecutor<Currency>() {

    override val querySorters = sortExecutors<Currency> {
        sort("code", compareBy { it.id.value })
        sort("name", compareBy(String.CASE_INSENSITIVE_ORDER) { it.name })
    }

    override val specificationExecutors = specificationExecutors<Currency> {
        uniqueValue("code") { it.id.value }
        string("name") { it.name }
    }
}
