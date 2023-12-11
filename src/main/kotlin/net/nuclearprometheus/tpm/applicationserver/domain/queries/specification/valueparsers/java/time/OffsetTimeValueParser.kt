package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.java.time

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParser
import java.time.OffsetTime

object OffsetTimeValueParser : ValueParser<OffsetTime> {
    override fun parse(value: String?): OffsetTime? {
        return value?.let { OffsetTime.parse(it) }
    }
}