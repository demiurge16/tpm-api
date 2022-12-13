package net.nuclearprometheus.translationprojectmanager.adapters.externalsources.dictionaries.models

import java.time.LocalDate

data class ISO6393Retirement(
    val id: String,
    val referenceName: String,
    val retirementReason: ISO6393RetirementReason,
    val changeTo: String?,
    val retirementRemedy: String?,
    val effective: LocalDate
)
