package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.common

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParser

object LongValueParser : ValueParser<Long> {
    override fun parse(value: String?): Long? {
        return value?.toLong()
    }
}