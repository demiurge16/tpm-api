package net.nuclearprometheus.tpm.applicationserver.domain.queries.search.valueparsers

class BooleanValueParser : ValueParser<Boolean> {
    override fun parse(value: String?): Boolean? {
        return value?.toBoolean()
    }
}
