package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.java.time

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParser
import java.time.Month

object MonthValueParser : ValueParser<Month> {
    override fun parse(value: String?): Month? {
        return value?.let { Month.valueOf(it) }
    }
}