package net.nuclearprometheus.tpm.applicationserver.domain.queries.search.valueparsers

import java.time.LocalDate

class LocalDateValueParser : ValueParser<LocalDate> {
    override fun parse(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it) }
    }
}

