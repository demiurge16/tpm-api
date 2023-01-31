package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.serialization

import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models.ISO6393Name

fun Map<String, String>.toISO6393Name() = ISO6393Name(
    id = this["Id"]!!,
    printName = this["Print_Name"]!!,
    invertedName = this["Inverted_Name"]!!,
)
