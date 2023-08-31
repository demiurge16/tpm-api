package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models

import com.opencsv.bean.CsvBindByName
import com.opencsv.bean.CsvCustomBindByName
import com.opencsv.bean.CsvDate
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.serialization.ISO6393RetirementReasonDeserializer
import java.time.LocalDate

data class ISO6393Retirement(
    @CsvBindByName(column = "Id") val id: String,
    @CsvBindByName(column = "Ref_Name") val referenceName: String,
    @CsvCustomBindByName(column = "Ret_Reason", converter = ISO6393RetirementReasonDeserializer::class) val retirementReason: ISO6393RetirementReason,
    @CsvBindByName(column = "Change_To") val changeTo: String?,
    @CsvBindByName(column = "Ret_Remedy") val retirementRemedy: String?,
    @CsvBindByName(column = "Effective") @CsvDate("yyyy-MM-dd") val effective: LocalDate
) {
    internal constructor() : this("", "", ISO6393RetirementReason.Change, null, null, LocalDate.MIN)
}
