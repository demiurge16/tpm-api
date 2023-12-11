package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.common

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParser

object DoubleValueParser : ValueParser<Double> {
    override fun parse(value: String?): Double? {
        return value?.toDouble()
    }
}