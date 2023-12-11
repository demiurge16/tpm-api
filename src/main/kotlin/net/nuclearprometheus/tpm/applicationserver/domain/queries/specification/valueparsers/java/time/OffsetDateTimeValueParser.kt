package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.java.time

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParser
import java.time.OffsetDateTime

object OffsetDateTimeValueParser : ValueParser<OffsetDateTime> {
    override fun parse(value: String?): OffsetDateTime? {
        return value?.let { OffsetDateTime.parse(it) }
    }
}