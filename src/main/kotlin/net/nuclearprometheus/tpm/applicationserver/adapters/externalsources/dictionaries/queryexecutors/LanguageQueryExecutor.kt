package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.queryexecutors

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Language
import net.nuclearprometheus.tpm.applicationserver.domain.queries.executors.*
import org.springframework.stereotype.Component

@Component
class LanguageQueryExecutor : QueryExecutor<Language>() {

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

    override val specificationExecutors: Map<String, SpecificationExecutor<Language, *>> = mapOf(
        uniqueValue("code") { it.id.value },
        string("name") { it.name },
        uniqueValue("iso6392t") { it.iso6392T },
        uniqueValue("iso6392b") { it.iso6392B },
        uniqueValue("iso6391") { it.iso6391 },
        enum("type") { it.type },
        enum("scope") { it.scope }
    )
}