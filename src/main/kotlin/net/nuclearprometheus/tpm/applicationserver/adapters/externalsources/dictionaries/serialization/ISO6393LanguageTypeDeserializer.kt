package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.serialization

import com.opencsv.bean.AbstractBeanField
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models.ISO6393LanguageType

class ISO6393LanguageTypeDeserializer : AbstractBeanField<String, ISO6393LanguageType>() {

    override fun convert(value: String?) = when (value) {
        "A" -> ISO6393LanguageType.Ancient
        "C" -> ISO6393LanguageType.Constructed
        "E" -> ISO6393LanguageType.Extinct
        "H" -> ISO6393LanguageType.Historical
        "L" -> ISO6393LanguageType.Living
        "S" -> ISO6393LanguageType.Special
        else -> throw IllegalArgumentException("Unknown ISO 639-3 language type: $this")
    }
}