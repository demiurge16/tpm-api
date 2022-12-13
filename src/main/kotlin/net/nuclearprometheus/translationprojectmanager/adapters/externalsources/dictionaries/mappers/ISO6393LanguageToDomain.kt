package net.nuclearprometheus.translationprojectmanager.adapters.externalsources.dictionaries.mappers

import net.nuclearprometheus.translationprojectmanager.adapters.externalsources.dictionaries.models.ISO6393Language
import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.Language
import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.LanguageCode

fun ISO6393Language.toDomain() = Language(
    code = LanguageCode(id),
    name = referenceName,
)
