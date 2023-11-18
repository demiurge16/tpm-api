package net.nuclearprometheus.tpm.applicationserver.domain.queries.search.valueparsers

import java.util.*

class UUIDValueParser : ValueParser<UUID> {
    override fun parse(value: String?): UUID? {
        return UUID.fromString(value)
    }
}