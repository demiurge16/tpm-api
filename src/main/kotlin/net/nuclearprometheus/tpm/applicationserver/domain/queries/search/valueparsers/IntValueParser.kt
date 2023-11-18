package net.nuclearprometheus.tpm.applicationserver.domain.queries.search.valueparsers

class IntValueParser : ValueParser<Int> {
    override fun parse(value: String?): Int? {
        return value?.toInt()
    }
}
