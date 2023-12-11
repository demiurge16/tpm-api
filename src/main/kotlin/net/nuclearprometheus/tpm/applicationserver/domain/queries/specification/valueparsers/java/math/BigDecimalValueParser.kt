package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.java.math

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParser
import java.math.BigDecimal

object BigDecimalValueParser : ValueParser<BigDecimal> {
    override fun parse(value: String?): BigDecimal? {
        return value?.let { BigDecimal(it) }
    }
}