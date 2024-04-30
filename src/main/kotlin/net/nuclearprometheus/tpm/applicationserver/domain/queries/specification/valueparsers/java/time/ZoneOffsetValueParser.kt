package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.java.time

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParser
import java.time.ZoneOffset

object ZoneOffsetValueParser : ValueParser<ZoneOffset> {
    override fun parse(value: String?): ZoneOffset? {
        return value?.let { ZoneOffset.of(it) }
    }
}