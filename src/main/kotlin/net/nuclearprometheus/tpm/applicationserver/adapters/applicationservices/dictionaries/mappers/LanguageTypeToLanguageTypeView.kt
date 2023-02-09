package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.LanguageTypeView
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageType

fun LanguageType.toView() = LanguageTypeView(
    code = this,
    name = title
)
