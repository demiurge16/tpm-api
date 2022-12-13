package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.responses.LanguageView

interface LanguageApplicationService {

    fun getLanguages(): List<LanguageView>
    fun getLanguage(code: String): LanguageView
    fun getLanguageByNameLike(name: String): List<LanguageView>
}