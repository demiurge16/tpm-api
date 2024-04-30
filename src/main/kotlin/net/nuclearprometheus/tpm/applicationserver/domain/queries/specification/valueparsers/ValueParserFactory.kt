package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.common.*
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.java.math.BigDecimalValueParser
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.java.math.BigIntegerValueParser
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.java.time.*
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.valueparsers.java.util.UUIDValueParser
import java.math.BigDecimal
import java.math.BigInteger
import java.time.*
import java.util.*
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
object ValueParserFactory {

    fun <TValue : Any> getParser(valueClass: KClass<TValue>): ValueParser<TValue> {
        return when (valueClass) {
            UUID::class -> UUIDValueParser as ValueParser<TValue>
            Char::class -> CharValueParser as ValueParser<TValue>
            String::class -> StringValueParser as ValueParser<TValue>
            Byte::class -> ByteValueParser as ValueParser<TValue>
            Short::class -> ShortValueParser as ValueParser<TValue>
            Int::class -> IntValueParser as ValueParser<TValue>
            Long::class -> LongValueParser as ValueParser<TValue>
            Float::class -> FloatValueParser as ValueParser<TValue>
            Double::class -> DoubleValueParser as ValueParser<TValue>
            BigDecimal::class -> BigDecimalValueParser as ValueParser<TValue>
            BigInteger::class -> BigIntegerValueParser as ValueParser<TValue>
            Boolean::class -> BooleanValueParser as ValueParser<TValue>
            LocalDate::class -> LocalDateValueParser as ValueParser<TValue>
            LocalDateTime::class -> LocalDateTimeValueParser as ValueParser<TValue>
            ZonedDateTime::class -> ZonedDateTimeValueParser as ValueParser<TValue>
            OffsetDateTime::class -> OffsetDateTimeValueParser as ValueParser<TValue>
            OffsetTime::class -> OffsetTimeValueParser as ValueParser<TValue>
            LocalTime::class -> LocalTimeValueParser as ValueParser<TValue>
            Instant::class -> InstantValueParser as ValueParser<TValue>
            Duration::class -> DurationValueParser as ValueParser<TValue>
            Period::class -> PeriodValueParser as ValueParser<TValue>
            DayOfWeek::class -> DayOfWeekValueParser as ValueParser<TValue>
            Month::class -> MonthValueParser as ValueParser<TValue>
            MonthDay::class -> MonthDayValueParser as ValueParser<TValue>
            Year::class -> YearValueParser as ValueParser<TValue>
            YearMonth::class -> YearMonthValueParser as ValueParser<TValue>
            ZoneId::class -> ZoneIdValueParser as ValueParser<TValue>
            ZoneOffset::class -> ZoneOffsetValueParser as ValueParser<TValue>
            Enum::class -> EnumValueParser(valueClass as KClass<Enum<*>>) as ValueParser<TValue>
            else -> throw IllegalArgumentException("Invalid value class: $valueClass")
        }
    }
}