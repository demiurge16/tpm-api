package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.common

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParser

object BooleanValueParser : ValueParser<Boolean> {
    override fun parse(value: String?): Boolean? {
        return value?.toBoolean()
    }
}