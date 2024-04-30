package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.common

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParser

object IntValueParser : ValueParser<Int> {
    override fun parse(value: String?): Int? {
        return value?.toInt()
    }
}