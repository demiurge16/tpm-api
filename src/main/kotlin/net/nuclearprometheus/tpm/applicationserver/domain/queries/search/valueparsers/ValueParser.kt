package net.nuclearprometheus.tpm.applicationserver.domain.queries.search.valueparsers

interface ValueParser<TValue> {
    fun parse(value: String?): TValue?

    fun parseCollection(value: String?): Collection<TValue>? {
        return value?.split(",")?.map { parse(it)!! }
    }
}
