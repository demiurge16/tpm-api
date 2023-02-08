package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models.ISO6393Language
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Language
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageCode

fun ISO6393Language.toDomain() = Language(
    code = LanguageCode(id),
    iso6392B = part2B,
    iso6392T = part2T,
    iso6391 = part1,
    scope = scope.toDomain(),
    type = languageType.toDomain(),
    name = referenceName,
)
