package net.nuclearprometheus.tpm.applicationserver.domain.queries.search.valueparsers

class FloatValueParser : ValueParser<Float> {
    override fun parse(value: String?): Float? {
        return value?.toFloat()
    }
}