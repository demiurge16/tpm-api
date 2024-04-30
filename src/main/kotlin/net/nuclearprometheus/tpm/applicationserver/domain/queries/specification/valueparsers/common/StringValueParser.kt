package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.common

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParser

object StringValueParser : ValueParser<String> {
    override fun parse(value: String?): String? {
        return value
    }
}