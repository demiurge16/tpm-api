package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models.ISO6393Language
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Language
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageCode

fun ISO6393Language.toDomain() = Language(
    code = LanguageCode(id),
    name = referenceName,
)
