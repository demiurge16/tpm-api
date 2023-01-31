package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.serialization

import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models.ISO6393Macrolanguage

fun Map<String, String>.toISO6393Macrolanguage() = ISO6393Macrolanguage(
    macrolanguageId = this["M_Id"]!!,
    individualLanguageId = this["I_Id"]!!,
    individualLanguageStatus = this["I_Status"]!!.asISO6393IndividualLanguageStatus(),
)


