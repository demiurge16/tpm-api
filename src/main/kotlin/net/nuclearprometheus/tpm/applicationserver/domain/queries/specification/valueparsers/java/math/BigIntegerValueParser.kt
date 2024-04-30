package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.java.math

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParser
import java.math.BigInteger

object BigIntegerValueParser : ValueParser<BigInteger> {
    override fun parse(value: String?): BigInteger? {
        return value?.let { BigInteger(it) }
    }
}