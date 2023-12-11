package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.common

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParser

object CharValueParser : ValueParser<Char> {
    override fun parse(value: String?): Char? {
        return value?.firstOrNull()
    }
}