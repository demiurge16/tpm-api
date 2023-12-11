package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.common

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParser

object ShortValueParser : ValueParser<Short> {
    override fun parse(value: String?): Short? {
        return value?.toShort()
    }
}