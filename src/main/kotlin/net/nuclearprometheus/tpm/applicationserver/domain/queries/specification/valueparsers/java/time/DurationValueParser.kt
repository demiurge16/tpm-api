package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.java.time

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParser
import java.time.Duration

object DurationValueParser : ValueParser<Duration> {
    override fun parse(value: String?): Duration? {
        return value?.let { Duration.parse(it) }
    }
}