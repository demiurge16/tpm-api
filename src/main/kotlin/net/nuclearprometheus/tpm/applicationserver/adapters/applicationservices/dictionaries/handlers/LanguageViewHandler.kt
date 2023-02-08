package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.LanguageView
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.Page
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Language

interface LanguageViewHandler {

    fun getLanguages(query: FilteredRequest<Language>): Page<LanguageView>
    fun getLanguage(code: String): LanguageView
    fun getLanguageByNameLike(name: String): List<LanguageView>
}