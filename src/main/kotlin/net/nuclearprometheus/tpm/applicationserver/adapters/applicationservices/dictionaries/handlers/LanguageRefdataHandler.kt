package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.LanguageScopeView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.LanguageTypeView

interface LanguageRefdataHandler {

    fun getScopes(): List<LanguageScopeView>
    fun getTypes(): List<LanguageTypeView>
}
