package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models

import com.opencsv.bean.CsvBindByName

data class ISO6393Name(
    @CsvBindByName(column = "Id") val id: String,
    @CsvBindByName(column = "Print_Name") val printName: String,
    @CsvBindByName(column = "Inverted_Name") val invertedName: String
) {
    internal constructor() : this("", "", "")
}
