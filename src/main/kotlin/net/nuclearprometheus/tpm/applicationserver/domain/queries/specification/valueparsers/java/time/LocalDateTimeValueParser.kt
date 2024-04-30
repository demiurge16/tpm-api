package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.java.time

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParser
import java.time.LocalDateTime

object LocalDateTimeValueParser : ValueParser<LocalDateTime> {
    override fun parse(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it) }
    }
}