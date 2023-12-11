package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.java.time

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParser
import java.time.LocalDate

object LocalDateValueParser : ValueParser<LocalDate> {
    override fun parse(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it) }
    }
}