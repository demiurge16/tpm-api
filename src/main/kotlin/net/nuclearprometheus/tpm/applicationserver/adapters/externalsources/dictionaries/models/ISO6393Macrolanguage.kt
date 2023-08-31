package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models

import com.opencsv.bean.CsvBindByName
import com.opencsv.bean.CsvCustomBindByName
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.serialization.ISO6393IndividualLanguageStatusDeserializer

data class ISO6393Macrolanguage(
    @CsvBindByName(column = "M_Id") val macrolanguageId: String,
    @CsvBindByName(column = "I_Id") val individualLanguageId: String,
    @CsvCustomBindByName(column = "I_Status", converter = ISO6393IndividualLanguageStatusDeserializer::class) val individualLanguageStatus: ISO6393IndividualLanguageStatus
) {
    internal constructor() : this("", "", ISO6393IndividualLanguageStatus.Active)
}