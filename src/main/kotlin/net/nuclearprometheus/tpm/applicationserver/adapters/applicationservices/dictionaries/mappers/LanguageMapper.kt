package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.Language as LanguageResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Language
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageScope
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageType

object LanguageMapper {

    fun Language.toView() = LanguageResponse(
        code = code.value,
        iso6392T = iso6392T,
        iso6392B = iso6392B,
        iso6391 = iso6391,
        scope = scope.toView(),
        type = type.toView(),
        name = name
    )

    fun LanguageScope.toView() = LanguageResponse.Scope(
        code = this,
        name = title
    )

    fun LanguageType.toView() = LanguageResponse.Type(
        code = this,
        name = title
    )
}