package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.serialization

import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models.ISO6393Retirement
import java.time.LocalDate

fun Map<String, String>.toISO6393Retirement() = ISO6393Retirement(
    id = this["Id"]!!,
    referenceName = this["Ref_Name"]!!,
    retirementReason = this["Ret_Reason"]!!.asISO6393RetirementReason(),
    changeTo = this["Change_To"],
    retirementRemedy = this["Ret_Remedy"],
    effective = LocalDate.parse(this["Effective"]!!),
)
