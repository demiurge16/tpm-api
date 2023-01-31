package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models

enum class ISO6393RetirementReason(val code: String, val description: String) {
    Change("C", "Change"),
    Duplicate("D", "Duplicate"),
    Nonexistent("N", "Nonexistent"),
    Split("S", "Split"),
    Merge("M", "Merge"),
}
