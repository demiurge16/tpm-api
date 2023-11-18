package net.nuclearprometheus.tpm.applicationserver.domain.queries.search.valueparsers

import java.time.LocalDateTime

class LocalDateTimeValueParser : ValueParser<LocalDateTime> {
    override fun parse(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it) }
    }
}
