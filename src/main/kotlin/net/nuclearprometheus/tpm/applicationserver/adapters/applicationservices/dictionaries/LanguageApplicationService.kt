package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.handlers.LanguageRefdataHandler
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.handlers.LanguageViewHandler
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Language
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service

@Service
class LanguageApplicationService(
    private val languageViewHandler: LanguageViewHandler,
    private val languageRefdataHandler: LanguageRefdataHandler
) {

    private val logger = loggerFor(this::class.java)

    fun getLanguages(query: FilteredRequest<Language>) = with(logger) {
        info("Language application service, method getLanguages")
        languageViewHandler.getLanguages(query)
    }

    fun getLanguage(code: String) = with(logger) {
        info("Language application service, method getLanguage")
        languageViewHandler.getLanguage(code)
    }

    fun getLanguageByNameLike(name: String) = with(logger) {
        info("Language application service, method getLanguageByNameLike")
        languageViewHandler.getLanguageByNameLike(name)
    }

    fun getScopes() = with(logger) {
        info("Language application service, method getScopes")
        languageRefdataHandler.getScopes()
    }

    fun getTypes() = with(logger) {
        info("Language application service, method getTypes")
        languageRefdataHandler.getTypes()
    }
}
