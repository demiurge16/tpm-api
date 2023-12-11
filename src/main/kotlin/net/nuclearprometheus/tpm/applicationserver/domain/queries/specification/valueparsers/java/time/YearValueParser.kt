package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.java.time

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParser
import java.time.Year

object YearValueParser : ValueParser<Year> {
    override fun parse(value: String?): Year? {
        return value?.let { Year.parse(it) }
    }
}