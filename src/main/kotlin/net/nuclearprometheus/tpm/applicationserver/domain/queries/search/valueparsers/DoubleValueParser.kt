package net.nuclearprometheus.tpm.applicationserver.domain.queries.search.valueparsers

class DoubleValueParser : ValueParser<Double> {
    override fun parse(value: String?): Double? {
        return value?.toDouble()
    }
}
