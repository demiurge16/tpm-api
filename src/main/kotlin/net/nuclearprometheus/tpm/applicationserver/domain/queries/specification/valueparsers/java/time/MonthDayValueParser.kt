package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.java.time

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParser
import java.time.MonthDay

object MonthDayValueParser : ValueParser<MonthDay> {
    override fun parse(value: String?): MonthDay? {
        return value?.let { MonthDay.parse(it) }
    }
}