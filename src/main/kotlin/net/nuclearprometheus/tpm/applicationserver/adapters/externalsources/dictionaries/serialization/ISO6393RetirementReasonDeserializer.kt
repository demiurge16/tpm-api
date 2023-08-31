package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.serialization

import com.opencsv.bean.AbstractBeanField
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models.ISO6393RetirementReason

class ISO6393RetirementReasonDeserializer : AbstractBeanField<String, ISO6393RetirementReason>() {
    override fun convert(value: String?) = when (value) {
        "C" -> ISO6393RetirementReason.Change
        "D" -> ISO6393RetirementReason.Duplicate
        "N" -> ISO6393RetirementReason.Nonexistent
        "S" -> ISO6393RetirementReason.Split
        "M" -> ISO6393RetirementReason.Merge
        else -> throw IllegalArgumentException("Unknown ISO 639-3 retirement reason code: $this")
    }
}