package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers

interface ValueParser<TValue> {
    fun parse(value: String?): TValue?

    fun parseCollection(value: String?): Collection<TValue?> {
        if (value == null) {
            return emptyList()
        }
        return value.split(",")
            .map { it.trim() }
            .map { parse(it) }
    }
}
