package net.nuclearprometheus.tpm.applicationserver.domain.queries.search.valueparsers

import java.time.ZonedDateTime

class ZonedDateTimeValueParser : ValueParser<ZonedDateTime> {
    override fun parse(value: String?): ZonedDateTime? {
        return value?.let { ZonedDateTime.parse(it) }
    }
}
