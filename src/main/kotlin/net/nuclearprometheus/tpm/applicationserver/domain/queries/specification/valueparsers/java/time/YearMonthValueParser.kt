package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.java.time

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParser
import java.time.YearMonth

object YearMonthValueParser : ValueParser<YearMonth> {
    override fun parse(value: String?): YearMonth? {
        return value?.let { YearMonth.parse(it) }
    }
}