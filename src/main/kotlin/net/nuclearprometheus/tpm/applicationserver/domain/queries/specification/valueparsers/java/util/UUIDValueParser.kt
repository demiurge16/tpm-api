package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.java.util

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParser
import java.util.*

object UUIDValueParser : ValueParser<UUID> {
    override fun parse(value: String?): UUID? {
        return value?.let { UUID.fromString(it) }
    }
}