package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models

enum class ISO6393IndividualLanguageStatus(val code: String, val description: String) {
    Active("A", "Active"),
    Retired("R", "Retired"),
}
