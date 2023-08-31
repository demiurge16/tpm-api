package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.serialization

import com.opencsv.bean.AbstractBeanField
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models.ISO6393LanguageScope
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageScope

class ISO6393LanguageScopeDeserializer : AbstractBeanField<String, LanguageScope>() {
    override fun convert(value: String?) = when (value) {
        "I" -> ISO6393LanguageScope.Individual
        "M" -> ISO6393LanguageScope.Macrolanguage
        "S" -> ISO6393LanguageScope.Special
        else -> throw IllegalArgumentException("Unknown ISO 639-3 language scope: $this")
    }
}