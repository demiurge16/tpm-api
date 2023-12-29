package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.queryexecutors

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Language
import net.nuclearprometheus.tpm.applicationserver.domain.queries.executors.*
import org.springframework.stereotype.Component

@Component
class LanguageQueryExecutor : QueryExecutor<Language>() {

    override val querySorters = sortExecutors<Language> {
        sort("code", compareBy { it.id.value })
        sort("name", compareBy(String.CASE_INSENSITIVE_ORDER) { it.name })
        sort("iso6392t", compareBy(nullsLast(String.CASE_INSENSITIVE_ORDER)) { it.iso6392T })
        sort("iso6392b", compareBy(nullsLast(String.CASE_INSENSITIVE_ORDER)) { it.iso6392B })
        sort("iso6391", compareBy(nullsLast(String.CASE_INSENSITIVE_ORDER)) { it.iso6391 })
    }

    override val specificationExecutors = specificationExecutors<Language> {
        uniqueValue("code") { it.id.value }
        string("name") { it.name }
        uniqueValue("iso6392t") { it.iso6392T }
        uniqueValue("iso6392b") { it.iso6392B }
        uniqueValue("iso6391") { it.iso6391 }
        enum("type") { it.type }
        enum("scope") { it.scope }
    }
}