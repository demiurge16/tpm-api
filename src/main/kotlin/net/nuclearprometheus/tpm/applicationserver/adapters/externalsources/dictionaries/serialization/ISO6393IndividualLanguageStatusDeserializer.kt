package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.serialization

import com.opencsv.bean.AbstractBeanField
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models.ISO6393IndividualLanguageStatus

class ISO6393IndividualLanguageStatusDeserializer : AbstractBeanField<String, ISO6393IndividualLanguageStatus>() {
    override fun convert(value: String?) = when (value) {
        "A" -> ISO6393IndividualLanguageStatus.Active
        "C" -> ISO6393IndividualLanguageStatus.Retired
        else -> throw IllegalArgumentException("Unknown ISO 639-3 individual language status: $this")
    }
}