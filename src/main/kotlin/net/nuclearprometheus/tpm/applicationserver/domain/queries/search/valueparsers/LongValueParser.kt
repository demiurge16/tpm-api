package net.nuclearprometheus.tpm.applicationserver.domain.queries.search.valueparsers

class LongValueParser : ValueParser<Long> {
    override fun parse(value: String?): Long? {
        return value?.toLong()
    }
}
