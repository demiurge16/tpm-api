package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.java.time

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParser
import java.time.ZoneId

object ZoneIdValueParser : ValueParser<ZoneId> {
    override fun parse(value: String?): ZoneId? {
        return value?.let { ZoneId.of(it) }
    }
}