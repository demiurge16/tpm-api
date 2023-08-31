package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models

import com.opencsv.bean.CsvBindByName
import com.opencsv.bean.CsvCustomBindByName
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.serialization.ISO6393LanguageScopeDeserializer
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.serialization.ISO6393LanguageTypeDeserializer

data class ISO6393Language(
    @CsvBindByName(column = "Id") val id: String,
    @CsvBindByName(column = "Part2B") val part2B: String?,
    @CsvBindByName(column = "Part2T") val part2T: String?,
    @CsvBindByName(column = "Part1") val part1: String?,
    @CsvCustomBindByName(column = "Scope", converter = ISO6393LanguageScopeDeserializer::class) val scope: ISO6393LanguageScope,
    @CsvCustomBindByName(column = "Language_Type", converter = ISO6393LanguageTypeDeserializer::class) val languageType: ISO6393LanguageType,
    @CsvBindByName(column = "Ref_Name") val referenceName: String,
    @CsvBindByName(column = "Comment") val comment: String?
) {
    internal constructor() : this("", null, null, null, ISO6393LanguageScope.Individual, ISO6393LanguageType.Ancient, "", null)
}
