package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.java.time

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParser
import java.time.ZonedDateTime

object ZonedDateTimeValueParser : ValueParser<ZonedDateTime> {
    override fun parse(value: String?): ZonedDateTime? {
        return value?.let { ZonedDateTime.parse(it) }
    }
}