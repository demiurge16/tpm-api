package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.common

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParser

object ByteValueParser : ValueParser<Byte> {
    override fun parse(value: String?): Byte? {
        return value?.toByte()
    }
}