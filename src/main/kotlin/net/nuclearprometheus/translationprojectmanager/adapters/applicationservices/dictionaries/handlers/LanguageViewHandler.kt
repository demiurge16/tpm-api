package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.handlers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.responses.LanguageView

interface LanguageViewHandler {

    fun getLanguages(): List<LanguageView>
    fun getLanguage(code: String): LanguageView
    fun getLanguageByNameLike(name: String): List<LanguageView>
}