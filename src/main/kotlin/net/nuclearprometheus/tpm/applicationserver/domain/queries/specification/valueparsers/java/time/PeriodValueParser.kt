package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.java.time

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParser
import java.time.Period

object PeriodValueParser : ValueParser<Period> {
    override fun parse(value: String?): Period? {
        return value?.let { Period.parse(it) }
    }
}