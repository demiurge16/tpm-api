package net.nuclearprometheus.tpm.applicationserver.domain.queries.search.valueparsers

import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
object ValueParserFactory {

    fun <TValue : Any> getParser(valueClass: KClass<TValue>): ValueParser<TValue> {
        return when (valueClass) {
            String::class -> StringValueParser() as ValueParser<TValue>
            Int::class -> IntValueParser() as ValueParser<TValue>
            Long::class -> LongValueParser() as ValueParser<TValue>
            Float::class -> FloatValueParser() as ValueParser<TValue>
            Double::class -> DoubleValueParser() as ValueParser<TValue>
            Boolean::class -> BooleanValueParser() as ValueParser<TValue>
            LocalDateValueParser::class -> LocalDateValueParser() as ValueParser<TValue>
            LocalDateTimeValueParser::class -> LocalDateTimeValueParser() as ValueParser<TValue>
            ZonedDateTimeValueParser::class -> ZonedDateTimeValueParser() as ValueParser<TValue>
            Enum::class -> EnumValueParser(valueClass as KClass<Enum<*>>) as ValueParser<TValue>
            else -> throw IllegalArgumentException("Invalid value class: $valueClass")
        }
    }
}
