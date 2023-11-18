package net.nuclearprometheus.tpm.applicationserver.domain.queries.search.valueparsers

class StringValueParser : ValueParser<String> {
    override fun parse(value: String?): String? {
        return value
    }
}
