package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.java.time

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParser
import java.time.DayOfWeek

object DayOfWeekValueParser : ValueParser<DayOfWeek> {
    override fun parse(value: String?): DayOfWeek? {
        return value?.let { DayOfWeek.valueOf(it) }
    }
}