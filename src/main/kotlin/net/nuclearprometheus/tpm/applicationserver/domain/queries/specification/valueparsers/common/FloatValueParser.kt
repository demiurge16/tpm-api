package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.common

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParser

object FloatValueParser : ValueParser<Float> {
    override fun parse(value: String?): Float? {
        return value?.toFloat()
    }
}