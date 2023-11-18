package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.queryexecutors

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Language
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageScope
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageType
import net.nuclearprometheus.tpm.applicationserver.domain.queries.executors.*
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.dsl.SearchSpecification
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.dsl.searchSpecification
import org.springframework.stereotype.Component

@Component
class LanguageQueryExecutor : InMemoryQueryExecutor<Language>() {

    override val querySorters: Map<String, Comparator<Language>> = mapOf(
        "code" to Comparator { o1, o2 -> compareValues(o1.id.value, o2.id.value) },
        "name" to Comparator { o1, o2 -> o1.name.compareTo(o2.name, ignoreCase = true) },
        "iso6392t" to Comparator { o1, o2 ->
            compareValues(
                o1.iso6392T.orEmpty().lowercase(),
                o2.iso6392T.orEmpty().lowercase()
            )
        },
        "iso6392b" to Comparator { o1, o2 ->
            compareValues(
                o1.iso6392B.orEmpty().lowercase(),
                o2.iso6392B.orEmpty().lowercase()
            )
        },
        "iso6391" to Comparator { o1, o2 ->
            compareValues(
                o1.iso6391.orEmpty().lowercase(),
                o2.iso6391.orEmpty().lowercase()
            )
        }
    )

    override val searchSpecification: SearchSpecification<Language> = searchSpecification {
        uniqueToken("code", String::class) using { it.id.value }
        string("name") using Language::name
        string("iso6392t") using Language::iso6392T
        string("iso6392b") using Language::iso6392B
        string("iso6391") using Language::iso6391
        enum("scope", LanguageScope::class) using Language::scope
        enum("type", LanguageType::class) using Language::type
    }
}