package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.LanguageView

interface LanguageViewHandler {

    fun getLanguages(): List<LanguageView>
    fun getLanguage(code: String): LanguageView
    fun getLanguageByNameLike(name: String): List<LanguageView>
}