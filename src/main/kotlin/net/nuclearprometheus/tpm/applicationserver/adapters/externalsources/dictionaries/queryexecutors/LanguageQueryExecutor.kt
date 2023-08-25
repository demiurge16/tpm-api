package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.queryexecutors

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Language
import net.nuclearprometheus.tpm.applicationserver.domain.queries.executors.*
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

    override val queryFilters: Map<String, Map<String, FilterExecutor<Language>>> = mapOf(
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
        ),
        "iso6392t" to mapOf(
            "eq" to equal { it.iso6392T },
            "contains" to contains { it.iso6392T },
            "null" to isNull { it.iso6392T },
            "empty" to isEmpty { it.iso6392T },
        ),
        "iso6392b" to mapOf(
            "eq" to equal { it.iso6392B },
            "contains" to contains { it.iso6392B },
            "null" to isNull { it.iso6392B },
            "empty" to isEmpty { it.iso6392B },
        ),
        "iso6391" to mapOf(
            "eq" to isNull { it.iso6391 },
            "contains" to contains { it.iso6391 },
            "null" to isNull { it.iso6391 },
            "empty" to isEmpty { it.iso6391 },
        ),
        "scope" to mapOf(
            "eq" to equal { it.scope },
            "all" to all { it.scope },
            "any" to any { it.scope },
            "none" to none { it.scope },
            "null" to isNull { it.scope }
        ),
    )
}