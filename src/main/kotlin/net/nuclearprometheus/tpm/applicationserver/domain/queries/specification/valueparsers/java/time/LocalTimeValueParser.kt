package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.java.time

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParser
import java.time.LocalTime

object LocalTimeValueParser : ValueParser<LocalTime> {
    override fun parse(value: String?): LocalTime? {
        return value?.let { LocalTime.parse(it) }
    }
}