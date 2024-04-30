package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.java.time

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParser
import java.time.Instant

object InstantValueParser : ValueParser<Instant> {
    override fun parse(value: String?): Instant? {
        return value?.let { Instant.parse(it) }
    }
}