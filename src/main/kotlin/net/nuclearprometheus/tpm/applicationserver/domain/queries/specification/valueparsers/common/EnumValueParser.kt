package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.common

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.ValueParser
import kotlin.reflect.KClass

class EnumValueParser<TValue : Enum<*>>(private val enumClass: KClass<TValue>) : ValueParser<TValue> {
    override fun parse(value: String?): TValue? {
        return enumClass.java.enumConstants.firstOrNull { it.name == value }
    }
}
